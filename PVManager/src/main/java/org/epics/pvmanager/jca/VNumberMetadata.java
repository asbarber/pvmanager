/*
 * Copyright 2010 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */

package org.epics.pvmanager.jca;

import gov.aps.jca.dbr.CTRL;
import gov.aps.jca.dbr.TIME;
import java.text.NumberFormat;
import org.epics.pvmanager.data.Display;

/**
 *
 * @author carcassi
 */
class VNumberMetadata<TValue extends TIME, TMetadata extends CTRL> extends VMetadata<TValue> implements Display {

    private final TMetadata metadata;

    VNumberMetadata(TValue dbrValue, TMetadata metadata, boolean disconnected) {
        super(dbrValue, disconnected);
        this.metadata = metadata;
    }

    @Override
    public Double getLowerDisplayLimit() {
        return (Double) metadata.getLowerDispLimit();
    }

    @Override
    public Double getLowerCtrlLimit() {
        return (Double) metadata.getLowerCtrlLimit();
    }

    @Override
    public Double getLowerAlarmLimit() {
        return (Double) metadata.getLowerAlarmLimit();
    }

    @Override
    public Double getLowerWarningLimit() {
        return (Double) metadata.getLowerWarningLimit();
    }

    @Override
    public String getUnits() {
        return metadata.getUnits();
    }

    @Override
    public NumberFormat getFormat() {
        // TODO: this needs to be revised
        return NumberFormat.getNumberInstance();
    }

    @Override
    public Double getUpperWarningLimit() {
        return (Double) metadata.getUpperWarningLimit();
    }

    @Override
    public Double getUpperAlarmLimit() {
        return (Double) metadata.getUpperAlarmLimit();
    }

    @Override
    public Double getUpperCtrlLimit() {
        return (Double) metadata.getUpperCtrlLimit();
    }

    @Override
    public Double getUpperDisplayLimit() {
        return (Double) metadata.getUpperDispLimit();
    }

}