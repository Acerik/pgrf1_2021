package fill;


import model.Point;
import raster.Raster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLineFiller extends Filler {

    private List<BorderLine> polygon;

    public ScanLineFiller(Raster raster){
        super(raster);
    }

    @Override
    public void fill() {

    }

    public void setPolygon(List<Point> points){
        polygon = new ArrayList<>();
        /*
        ScanLineLine scanLineLine = new ScanLineLine(points.get(0).getX(), points.get(0).getY(),
                points.get(1).getX(), points.get(1).getY());
        polygon.add(scanLineLine);
         */
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        for(int i = 0; i < points.size() - 1; i++){
            int idx = (i+1) % points.size();

            BorderLine line = new BorderLine(points.get(i).getX(), points.get(i).getY(),
                    points.get(idx).getX(), points.get(idx).getY());

            if(!line.isHorizontal()) {
                line.orientate();
                if(yMin > line.y1){
                    yMin = line.y1;
                }
                if(yMax < line.y2){
                    yMax = line.y2;
                }
                polygon.add(line);
            }
        }
        List<Integer> intersections = new ArrayList<>();
        for(int y = yMin; y <= yMax; y++){
            //najdi všechny průsečíky (s line v polygon) pro dané y
            for (BorderLine line : polygon) {
                if(line.isIntersection(y)){
                    intersections.add(line.getIntersection(y));
                }
            }
            // seradit podle x souradnice (průsečíky) vlastní algoritmus bubblesort (složitější za bonus. bod napsat důvod(quicksort))
            Collections.sort(intersections);
            // spojím sudý s lichým
            for(int i = 0; i < intersections.size()-1; i += 2){
                int x1 = intersections.get(i);
                int x2 = intersections.get(i+1);
                for(int x = x1; x <= x2; x++){
                    raster.setPixel(x, y, 0x0F0F0);
                }
            }
        }
    }

    private class BorderLine {
        int x1, y1, x2, y2;

        public BorderLine(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public boolean isHorizontal(){
            return (y1 == y2);
        }

        public void orientate(){
            if(y1 > y2){
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        }

        public boolean isIntersection(int y) {
            return y1 <= y && y >= y2;
        }

        public int getIntersection(int y){
            return 0; //dopočítat x
        }
    }
}
