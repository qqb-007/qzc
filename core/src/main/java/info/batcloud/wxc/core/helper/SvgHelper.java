package info.batcloud.wxc.core.helper;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class SvgHelper {

    public static void convertToJpg(String svgCode, OutputStream os, float width, float height) throws TranscoderException {
        try {
            byte[] bytes = svgCode.getBytes("utf-8");
            JPEGTranscoder t = new JPEGTranscoder();
            t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,
                    new Float(.8));
            TranscoderInput input = new TranscoderInput(
                    new ByteArrayInputStream(bytes));

            TranscoderOutput output = new TranscoderOutput(os);
            t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
            t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height);
            t.transcode(input, output);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
