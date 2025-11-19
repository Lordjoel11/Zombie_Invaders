package com.marcosguianfrancojoel.zombie_invaders.Componentes;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;


import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class ComponenteActualizarAngulo extends Component {

    Entity entityObjetiva;
    Entity entidadObjetiva;
    Point2D puntoObjetivo;
    Point2D mouseDir;
    private boolean autoPoint2dMouse = false;

     Point2D direction = new Point2D(1,1);



    public ComponenteActualizarAngulo(Entity entidadObjetiva) {
        this.entidadObjetiva = entidadObjetiva;
    }

    public ComponenteActualizarAngulo(Point2D puntoObjetivo) {
        this.puntoObjetivo = puntoObjetivo;
    }

    public ComponenteActualizarAngulo() {
        this.autoPoint2dMouse = true;
    }

    @Override
    public void onUpdate(double tpf) {
        actualizarAngulo();
    }

    private void actualizarAngulo()
    {
        if (autoPoint2dMouse) {
            actualizarPorMouse();
            return;
        }

        if (entidadObjetiva == null) {
            if (puntoObjetivo != null) {
                actualizarPorPunto2D(puntoObjetivo);
            }
            return;
        }

        Point2D centroEntidad = entity.getCenter();
        Point2D entidadPos = entidadObjetiva.getCenter();

        Point2D dir = entidadPos.subtract(centroEntidad);

        double angulo = Math.toDegrees(Math.atan2(dir.getY(), dir.getX()));

        entity.setRotation(angulo);
        direction = dir.normalize();
    }

    private void actualizarPorPunto2D(Point2D punto2D)
    {

        System.out.println("Actualizar Punto");
        Point2D centroEntidad = entity.getCenter();


        Point2D dir = punto2D.subtract(centroEntidad);

        double angle = Math.toDegrees(Math.atan2(dir.getY(), dir.getX()));

        entity.setRotation(angle);
        direction = dir.normalize();
    }

    private void actualizarPorMouse()
    {
        Point2D mouseDir = new Point2D(getInput().getMouseXWorld(), getInput().getMouseYWorld());
        Point2D entidadCentro = entity.getCenter();

        Point2D dir = mouseDir.subtract(entidadCentro);

        double angle = Math.toDegrees(Math.atan2(dir.getY(), dir.getX()));

        entity.setRotation(angle);
        direction = dir.normalize();
    }

    public Point2D getDirection() {
        return direction;
    }
}
