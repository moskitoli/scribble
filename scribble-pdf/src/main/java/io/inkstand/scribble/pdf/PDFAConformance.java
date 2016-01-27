package io.inkstand.scribble.pdf;

import org.apache.pdfbox.preflight.Format;

/**
 * Levels of PDF/A conformance
 */
public enum PDFAConformance {

    PDFA_1A(Format.PDF_A1A),
    PDFA_1B(Format.PDF_A1B),;

    private final Format format;

    PDFAConformance(Format format) {
        this.format = format;
    }

    Format getFormat() {
        return format;
    }
}
