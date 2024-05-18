package jsl.entitygraphs;

import jsl.entitygraphs.entities.Location;

public record GetLocation() {
    public static Location location = new Location("wills@email.com", "United State", "Georgia",
            "Atlanta", "123 Mountain View");
}
