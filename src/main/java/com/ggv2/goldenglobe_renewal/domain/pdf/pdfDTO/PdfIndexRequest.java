package com.ggv2.goldenglobe_renewal.domain.pdf.pdfDTO;

import java.net.URL;

public record PdfIndexRequest(Long travel_list_id, URL pdf_url) {
}
