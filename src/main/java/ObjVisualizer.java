import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.util.List;

public class ObjVisualizer {

    private final String title;
    private final int segments;
    private final int width;
    private final int height;
    private final int fps;

    private ObjVisualizer(Builder builder) {
        title = builder.title;
        segments = builder.segments;
        width = builder.width;
        height = builder.height;
        fps = builder.fps;
    }

    public void render(List<Edge> edges) {

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GLCanvas glCanvas = new GLCanvas();
        JoglRenderer renderer = new JoglRenderer(edges, segments);
        glCanvas.addGLEventListener(renderer);

        frame.add(glCanvas);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        FPSAnimator animator = new FPSAnimator(glCanvas, fps);
        animator.start();
    }

    public static final class Builder {
        private String title;
        private int segments;
        private int width;
        private int height;
        private int fps;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Builder withSegments(int val) {
            segments = val;
            return this;
        }

        public Builder withWidth(int val) {
            width = val;
            return this;
        }

        public Builder withHeight(int val) {
            height = val;
            return this;
        }

        public Builder withFps(int val) {
            fps = val;
            return this;
        }

        public ObjVisualizer build() {
            return new ObjVisualizer(this);
        }
    }
}
