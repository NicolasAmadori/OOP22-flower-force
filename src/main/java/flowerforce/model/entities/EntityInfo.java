package flowerforce.model.entities;

import javafx.util.Pair;

/**
 * This class models the info that must be saved about an entity in order to recognise it using maps.
 * It extends {@link Pair} and override just the method {@link Pair#equals(Object)}.
 */
public class EntityInfo<X,Y> extends Pair<X,Y> {

    /**
     * 
     * @param key of the pair
     * @param value of the pair
     */
    public EntityInfo(final X key, final Y value) {
        super(key, value);
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj;
    }

}
