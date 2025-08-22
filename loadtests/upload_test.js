import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  scenarios: {
    ramp_upload: {
      executor: 'ramping-vus',
      stages: [
        { duration: '15s', target: 3 },
        { duration: '45s', target: 10 },
        { duration: '15s', target: 0 },
      ],
    },
  },
  thresholds: {
    'http_req_failed': ['rate<0.02'],
    'http_req_duration{endpoint:upload}': ['p(95)<1500'],
  },
  summaryTrendStats: ['avg','min','med','p(90)','p(95)','p(99)','max'],
};

const BASE_URL  = __ENV.BASE_URL  || 'http://localhost:8080';
const TRAVEL_ID = __ENV.TRAVEL_ID || '2';
const TOKEN     = __ENV.TOKEN; // 업로드는 토큰 직접 주는 게 편함

const filePath = __ENV.FILE || './sample.pdf';  // 스크립트 기준 경로
const bin = open(filePath, 'b');

export default function () {
  if (!TOKEN) throw new Error('TOKEN env 필요: -e TOKEN=...');

  const formData = { file: http.file(bin, 'sample.pdf', 'application/pdf') };
  const res = http.post(`${BASE_URL}/api/travels/${TRAVEL_ID}/pdfs`, formData, {
    headers: { Authorization: `Bearer ${TOKEN}` },
    tags: { endpoint: 'upload' },
  });

  check(res, { 'upload 200/202': (r) => r.status === 200 || r.status === 202 });
  sleep(1);
}

export function handleSummary(data) {
  return { 'upload-summary.json': JSON.stringify(data, null, 2) };
}
