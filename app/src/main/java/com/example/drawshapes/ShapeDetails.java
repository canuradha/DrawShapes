package com.example.drawshapes;

import android.graphics.Paint;
import android.graphics.Path;

public class ShapeDetails {
    private Path shapePath;
    private Paint strokeInfo;

    public ShapeDetails(Path path, Paint pencil){
        this.shapePath = path;
        this.strokeInfo = pencil;
    }



    public Path getShapePath() {
        return shapePath;
    }

    public void setShapePath(Path shapePath) {
        this.shapePath = shapePath;
    }

    public Paint getStrokeInfo() {
        return strokeInfo;
    }

    public void setStrokeInfo(Paint strokeInfo) {
        this.strokeInfo = strokeInfo;
    }
}
