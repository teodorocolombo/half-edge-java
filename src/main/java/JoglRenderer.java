import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JoglRenderer implements GLEventListener {

    private final List<Edge> allEdges;
    private final int segments;

    public JoglRenderer(List<Edge> edges, int segments) {
        this.allEdges = edges;
        this.segments = segments;
    }

    private void drawSegmentByLineEquation(GL2 gl, float x1, float y1, float x2, float y2) {
        int numSegments = segments;

        float dx = x2 - x1;
        float dy = y2 - y1;

        for (int i = 0; i <= numSegments; i++) {
            float t = (float) i / (float) numSegments;

            float x = x1 + t * dx;
            float y = y1 + t * dy;

            gl.glVertex2f(x, y);
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        gl.glColor3f(1.0f, 1.0f, 1.0f);

        Set<Edge> drawnEdges = new HashSet<>();

        gl.glBegin(GL2.GL_POINTS);

        for (Edge currentEdge : allEdges) {
            if (drawnEdges.contains(currentEdge)) {
                continue;
            }

            Vertex v1 = currentEdge.originVertex();
            Vertex v2 = currentEdge.next().originVertex();

            drawSegmentByLineEquation(gl, v1.x(), v1.y(), v2.x(), v2.y());

            drawnEdges.add(currentEdge);
            if (currentEdge.pair() != null) {
                drawnEdges.add(currentEdge.pair());
            }
        }

        gl.glEnd();
        gl.glFlush();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(-2.0, 2.0, -2.0, 2.0, -1.0, 1.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}