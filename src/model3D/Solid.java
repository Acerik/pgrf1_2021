package model3D;

import transforms.Point3D;

import java.util.List;

public class Solid { // vytvořit těleso
    private List<Integer> indexBuffer;
    private List<Point3D> vertexBuffer;

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public void setIndexBuffer(List<Integer> indexBuffer) {
        this.indexBuffer = indexBuffer;
    }

    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public void setVertexBuffer(List<Point3D> vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }
}
