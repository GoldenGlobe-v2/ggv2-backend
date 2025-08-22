import http from 'k6/http';
import { check, sleep, group } from 'k6';
import { Trend } from 'k6/metrics';

export const options = {
  scenarios: {
    ramp_load: {
      executor: 'ramping-vus',
      stages: [
        { duration: '20s', target: 5 },
        { duration: '1m',  target: 20 },
        { duration: '20s', target: 0 },
      ],
    },
  },
  thresholds: {
    'http_req_failed': ['rate<0.01'],
    'http_req_duration{endpoint:chat}': ['p(95)<900', 'p(99)<1500'],
    'http_req_duration{endpoint:travels}': ['p(95)<300'],
  },
  summaryTrendStats: ['avg','min','med','p(90)','p(95)','p(99)','max'],
};

const BASE_URL  = __ENV.BASE_URL  || 'http://localhost:8080';
const TRAVEL_ID = __ENV.TRAVEL_ID || '2';
const chatDuration = new Trend('chat_duration_ms');

function tryParseToken(res) {
  // ⚠️ 네 로그인 응답 JSON에 맞게 키를 정해줘
  return res.json('accessToken') || res.json('access_token') || res.json('token');
}

export function setup() {
  if (__ENV.TOKEN) return { token: __ENV.TOKEN };

  const loginUrl = `${BASE_URL}/api/auth/login`;
  const payload = JSON.stringify({
    cellphone: __ENV.CELL || '01012345678',
    password:  __ENV.PASS || 'password'
  });
  const res = http.post(loginUrl, payload, { headers: { 'Content-Type': 'application/json' } });
  check(res, { 'login 200': (r) => r.status === 200 });
  const token = tryParseToken(res);
  if (!token) throw new Error('로그인 토큰 파싱 실패: -e TOKEN=... 또는 파싱 키 수정 필요');
  return { token };
}

export default function (data) {
  const headers = {
    'Authorization': `Bearer ${data.token}`,
    'Content-Type': 'application/json',
  };

  group('travels-list', () => {
    const r = http.get(`${BASE_URL}/api/travels`, { headers, tags: { endpoint: 'travels' } });
    check(r, { 'travels 200': (resp) => resp.status === 200 });
  });

  group('chat', () => {
    const questions = [
      '항공권 예약번호 알려줘',
      '호텔 예약 확인번호가 뭐야?',
      '여행 일정 요약해줘',
      '수하물 규정 알려줘',
      '체크인 시간은 언제야?',
    ];
    const q = questions[Math.floor(Math.random() * questions.length)];
    const body = JSON.stringify({ question: q });

    const t0 = Date.now();
    const r = http.post(`${BASE_URL}/api/travels/${TRAVEL_ID}/chat`, body, {
      headers, tags: { endpoint: 'chat' }
    });
    chatDuration.add(Date.now() - t0);

    check(r, {
      'chat 200': (resp) => resp.status === 200,
      'chat has answer': (resp) => !!resp.json('answer'),
    });
  });

  sleep(1);
}

export function handleSummary(data) {
  return { 'summary.json': JSON.stringify(data, null, 2) };
}
