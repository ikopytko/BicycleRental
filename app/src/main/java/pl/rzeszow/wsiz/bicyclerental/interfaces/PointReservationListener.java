package pl.rzeszow.wsiz.bicyclerental.interfaces;

import pl.rzeszow.wsiz.bicyclerental.api.model.Point;

/**
 * Created by Roman Savka on
 * 04-Apr-15.
 */
public interface PointReservationListener {
    void reservePoint(Point pointId);
}
