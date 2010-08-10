/*
 * Copyright 2010 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */

package org.epics.pvmanager.data;

import org.epics.pvmanager.NullUtils;
import org.epics.pvmanager.TypeSupport;

/**
 * Adds support for EPICS standard types.
 *
 * @author carcassi
 */
class EpicsTypeSupport {

    private static boolean installed = false;

    static void install() {
        // Install only once
        if (installed)
            return;

        addScalar();

        installed = true;
    }

    private static void addScalar() {
        // Add support for all scalars: simply return the new value
        TypeSupport.addTypeSupport(Scalar.class, new TypeSupport<Scalar>() {
            @Override
            public Notification<Scalar> prepareNotification(Scalar oldValue, Scalar newValue) {
                if (NullUtils.equalsOrBothNull(oldValue, newValue))
                    return new Notification<Scalar>(false, null);
                return new Notification<Scalar>(true, newValue);
            }
        });
    }
    

}