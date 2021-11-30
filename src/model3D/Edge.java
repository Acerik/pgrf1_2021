package model3D;

import transforms.Point3D;

public class Edge extends Solid{
    public Edge(){
        getVertexBuffer().add(new Point3D(0,0,0));
        getVertexBuffer().add(new Point3D(1,1,1));

        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
    }
}
