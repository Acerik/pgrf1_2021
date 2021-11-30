package Render;

import model3D.Scene;
import model3D.Solid;
import raster.Raster;
import rasterize.LineRasterizer;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4Transl;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

public class EdgeRenderer implements Renderer{

    LineRasterizer lineRasterizer;

    private Mat4 model = new Mat4Identity();
    private Mat4 view = new Mat4Identity();
    private Mat4 proj = new Mat4Identity();
    @Override
    public void setModel(Mat4 model) {
        this.model = model;
    }

    @Override
    public void setView(Mat4 view) {
        this.view = view;
    }

    @Override
    public void setProjection(Mat4 proj) {
        this.proj = proj;
    }

    @Override
    public void render(Solid solid) {
        List<Point3D> vbTemp = new ArrayList<>();
        Mat4 trans = model.mul(view).mul(proj);

        for(int i = 0; i < solid.getVertexBuffer().size(); i++){
            Point3D temp = solid.getVertexBuffer().get(i);

            //temp = temp.mul(model);
            //temp = temp.mul(view);
            //temp = temp.mul(proj);

            temp = temp.mul(trans);
            vbTemp.add(temp);
        }

        for(int i = 0; i < solid.getIndexBuffer().size(); i++){
            int idx = i+1 % solid.getIndexBuffer().size();
            //render(solid.getVertexBuffer().get(i), solid.getVertexBuffer().get(idx));
            render(vbTemp.get(i), vbTemp.get(idx));
        }
    }

    @Override
    public void render(Scene scene) {

    }

    public void render(Point3D a, Point3D b){
        // clip

        // dehomog

        // 3D -> 2D

        // viewport - done - přepočet podle velikosti okna od -1 do 1 -> 0 je střed
        // draw line - done
        int x1,y1,x2,y2;
        x1 = (int)(lineRasterizer.getRaster().getWidth() / 2 * (1 + a.getX()));
        y1 = (int)(lineRasterizer.getRaster().getHeight() / 2 * (1 - a.getY()));
        x2 = (int)(lineRasterizer.getRaster().getWidth() / 2 * (1 + b.getX()));
        y2 = (int)(lineRasterizer.getRaster().getHeight() / 2 * (1 - b.getY()));

        lineRasterizer.rasterize(x1,y1,x2,y2);
    }

    @Override
    public void setRaster(Raster raster) {

    }

    public void setRasterizer(LineRasterizer lineRasterizer){
        this.lineRasterizer = lineRasterizer;
    }
}
