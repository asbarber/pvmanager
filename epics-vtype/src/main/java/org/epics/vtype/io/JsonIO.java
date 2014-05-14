/**
 * Copyright (C) 2010-14 pvmanager developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.vtype.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.epics.util.array.ArrayByte;
import org.epics.util.array.ArrayDouble;
import org.epics.util.array.ArrayInt;
import org.epics.util.array.ListByte;
import org.epics.util.array.ListDouble;
import org.epics.util.array.ListFloat;
import org.epics.util.array.ListInt;
import org.epics.util.array.ListLong;
import org.epics.util.array.ListNumber;
import org.epics.util.array.ListShort;
import org.epics.util.time.Timestamp;
import org.epics.vtype.AlarmSeverity;
import org.epics.vtype.ArrayDimensionDisplay;
import org.epics.vtype.VBoolean;
import org.epics.vtype.VByte;
import org.epics.vtype.VByteArray;
import org.epics.vtype.VDouble;
import org.epics.vtype.VDoubleArray;
import org.epics.vtype.VEnum;
import org.epics.vtype.VEnumArray;
import org.epics.vtype.VExtendedAlarm;
import org.epics.vtype.VExtendedAlarm.AlarmCondition;
import org.epics.vtype.VFloat;
import org.epics.vtype.VFloatArray;
import org.epics.vtype.VImage;
import org.epics.vtype.VInt;
import org.epics.vtype.VIntArray;
import org.epics.vtype.VLong;
import org.epics.vtype.VLongArray;
import org.epics.vtype.VMultiDouble;
import org.epics.vtype.VMultiEnum;
import org.epics.vtype.VMultiInt;
import org.epics.vtype.VMultiString;
import org.epics.vtype.VNumber;
import org.epics.vtype.VNumberArray;
import org.epics.vtype.VShort;
import org.epics.vtype.VShortArray;
import org.epics.vtype.VStatistics;
import org.epics.vtype.VString;
import org.epics.vtype.VStringArray;
import org.epics.vtype.VTable;
import org.epics.vtype.VType;
import org.epics.vtype.ValueFactory;
import static org.epics.vtype.ValueFactory.newAlarm;
import static org.epics.vtype.ValueFactory.newDisplay;
import static org.epics.vtype.ValueFactory.newDisplay;
import static org.epics.vtype.ValueFactory.newTime;
import static org.epics.vtype.ValueFactory.newTime;
import static org.epics.vtype.ValueFactory.newVNumberArray;
import static org.epics.vtype.ValueFactory.newVNumberArray;

/**
 *
 * @author barberaa
 */
public class JsonIO {
    
    interface SVType<T extends VType> {
        T toVType();
    }
    
    class SVBoolean implements SVType<VBoolean> {
        
        private final Boolean value;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        SVBoolean(VBoolean v) {
            value = v.getValue();
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
        }
        
        @Override
        public VBoolean toVType() {
            return ValueFactory.newVBoolean(
                    value,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid)
            );
        }
    }
    
    class SVByte implements SVType<VByte> {
        
        private final Byte value;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVByte(VByte v) {
            value = v.getValue();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VByte toVType() {
            return ValueFactory.newVByte(
                    value,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    class SVByteArray implements SVType<VByteArray> {
        
        private final ListByte value;
        private final ListInt sizes;
        private final List<ArrayDimensionDisplay> dimensions;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVByteArray(VByteArray v) {
            value = v.getData();
            sizes = v.getSizes();
            dimensions = v.getDimensionDisplay();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VByteArray toVType() {
            return (VByteArray) ValueFactory.newVNumberArray(
                    value,
                    sizes,
                    dimensions,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    
    class SVDouble implements SVType<VDouble> {
        
        private final Double value;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVDouble(VDouble v) {
            value = v.getValue();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VDouble toVType() {
            return ValueFactory.newVDouble(
                    value,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    class SVDoubleArray implements SVType<VDoubleArray> {
        
        private final ListDouble value;
        private final ListInt sizes;
        private final List<ArrayDimensionDisplay> dimensions;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVDoubleArray(VDoubleArray v) {
            value = v.getData();
            sizes = v.getSizes();
            dimensions = v.getDimensionDisplay();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VDoubleArray toVType() {
            return (VDoubleArray) ValueFactory.newVNumberArray(
                    value,
                    sizes,
                    dimensions,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    
    class SVEnum implements SVType<VEnum> {
        
        private final String value;
        private final int index;
        private final List<String> labels;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        SVEnum(VEnum v) {
            value = v.getValue();
            index = v.getIndex();
            labels = v.getLabels();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
        }
        
        @Override
        public VEnum toVType() {
            return ValueFactory.newVEnum(
                    index,
                    labels,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid)
            );
        }
    }
    class SVEnumArray implements SVType<VEnumArray> {
        
        private final List<String> value;
        private final ListInt sizes;
        private final ListInt indices;
        private final List<String> labels;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        
        SVEnumArray(VEnumArray v) {
            value = v.getData();
            sizes = v.getSizes();
            indices = v.getIndexes();
            labels = v.getLabels();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
        }
         
        @Override
        public VEnumArray toVType() {
            return ValueFactory.newVEnumArray(
                    indices,
                    labels,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid)
            );            
        }
        
    }
    
    class SVExtendedAlarm implements SVType<VExtendedAlarm> {

        private final List<AlarmCondition> conditions;
        private final String message;
        
        SVExtendedAlarm(VExtendedAlarm v){
            conditions = v.getConditions();
            message = v.getMessage();
        }
        
        @Override
        public VExtendedAlarm toVType() {
            //TODO: support this
            throw new UnsupportedOperationException(); 
        }
        
    }
    
    class SVFloat implements SVType<VFloat> {
        
        private final Float value;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVFloat(VFloat v) {
            value = v.getValue();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VFloat toVType() {
            return ValueFactory.newVFloat(
                    value,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    class SVFloatArray implements SVType<VFloatArray> {
        
        private final ListFloat value;
        private final ListInt sizes;
        private final List<ArrayDimensionDisplay> dimensions;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVFloatArray(VFloatArray v) {
            value = v.getData();
            sizes = v.getSizes();
            dimensions = v.getDimensionDisplay();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VFloatArray toVType() {
            return (VFloatArray) ValueFactory.newVNumberArray(
                    value,
                    sizes,
                    dimensions,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    
    class SVImage implements SVType<VImage>{
        private final int width;
        private final int height;
        private final byte[] bytes;
        
        SVImage(VImage v){
            width = v.getWidth();
            height = v.getHeight();
            bytes = v.getData();
        }
        
        @Override
        public VImage toVType() {
            return ValueFactory.newVImage(
                    width,
                    height,
                    bytes
            );
        }
        
    }
    
    class SVInt implements SVType<VInt> {
        
        private final Integer value;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVInt(VInt v) {
            value = v.getValue();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VInt toVType() {
            return ValueFactory.newVInt(
                    value,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    class SVIntArray implements SVType<VIntArray> {
        
        private final ListInt value;
        private final ListInt sizes;
        private final List<ArrayDimensionDisplay> dimensions;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVIntArray(VIntArray v) {
            value = v.getData();
            sizes = v.getSizes();
            dimensions = v.getDimensionDisplay();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VIntArray toVType() {
            return (VIntArray) ValueFactory.newVNumberArray(
                    value,
                    sizes,
                    dimensions,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    
    class SVLong implements SVType<VLong> {
        
        private final Long value;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVLong(VLong v) {
            value = v.getValue();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VLong toVType() {
            return ValueFactory.newVLong(
                    value,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    class SVLongArray implements SVType<VLongArray> {
        
        private final ListLong value;
        private final ListInt sizes;
        private final List<ArrayDimensionDisplay> dimensions;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVLongArray(VLongArray v) {
            value = v.getData();
            sizes = v.getSizes();
            dimensions = v.getDimensionDisplay();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VLongArray toVType() {
            return (VLongArray) ValueFactory.newVNumberArray(
                    value,
                    sizes,
                    dimensions,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    
    class SVMultiDouble implements SVType<VMultiDouble> {
        
        private final List<SVDouble> values;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVMultiDouble(VMultiDouble v) {
            values = new ArrayList<>();
            for (VDouble d: v.getValues()){
                values.add(new SVDouble(d));
            }
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VMultiDouble toVType() {
            List<VDouble> list = new ArrayList<>();
            for (SVDouble d: values){
                list.add(d.toVType());
            }
            
            return ValueFactory.newVMultiDouble(
                    list,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }    
    class SVMultiEnum implements SVType<VMultiEnum> {
        
        private final List<SVEnum> values;
        private final List<String> labels;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        SVMultiEnum(VMultiEnum v) {
            values = new ArrayList<>();
            for (VEnum e: v.getValues()){
                values.add(new SVEnum(e));
            }
            labels = v.getLabels();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
        }
        
        @Override
        public VMultiEnum toVType() {
            //TODO: support this
            throw new UnsupportedOperationException();
        }
    }
    class SVMultiInt implements SVType<VMultiInt> {
        
        private final List<SVInt> values;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVMultiInt(VMultiInt v) {
            values = new ArrayList<>();
            for (VInt d: v.getValues()){
                values.add(new SVInt(d));
            }
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VMultiInt toVType() {
            //TODO: support this
            throw new UnsupportedOperationException();         
        }
        
    }    
    class SVMultiString implements SVType<VMultiString> {
        
        private final List<SVString> values;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        SVMultiString(VMultiString v) {
            values = new ArrayList<>();
            for (VString d: v.getValues()){
                values.add(new SVString(d));
            }
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
        }
         
        @Override
        public VMultiString toVType() {
            //TODO: support this
            throw new UnsupportedOperationException();         
        }
        
    }    
    
    class SVNumber implements SVType<VNumber> {
        
        private final Number value;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVNumber(VNumber v) {
            value = v.getValue();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VNumber toVType() {
            return ValueFactory.newVNumber(
                    value,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    class SVNumberArray implements SVType<VNumberArray> {
        
        private final ListNumber value;
        private final ListInt sizes;
        private final List<ArrayDimensionDisplay> dimensions;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVNumberArray(VNumberArray v) {
            value = v.getData();
            sizes = v.getSizes();
            dimensions = v.getDimensionDisplay();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VNumberArray toVType() {
            return (VNumberArray) ValueFactory.newVNumberArray(
                    value,
                    sizes,
                    dimensions,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
        
    class SVShort implements SVType<VShort> {
        private final Short value;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVShort(VShort v) {
            value = v.getValue();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VShort toVType() {
            return ValueFactory.newVShort(
                    value,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    class SVShortArray implements SVType<VShortArray> {
        
        private final ListShort value;
        private final ListInt sizes;
        private final List<ArrayDimensionDisplay> dimensions;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVShortArray(VShortArray v) {
            value = v.getData();
            sizes = v.getSizes();
            dimensions = v.getDimensionDisplay();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
         
        @Override
        public VShortArray toVType() {
            return (VShortArray) ValueFactory.newVNumberArray(
                    value,
                    sizes,
                    dimensions,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    
    class SVStatistics implements SVType<VStatistics> {
        
        private final Double average;
        private final Double stdDev;
        private final Double min;
        private final Double max;
        private final Integer nSamples;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        private final Double lowerDisplayLimit;
        private final Double lowerCtrlLimit;
        private final Double lowerAlarmLimit;
        private final Double lowerWarningLimit;
        private final String units;
        private final NumberFormat format;
        private final Double upperWarningLimit;
        private final Double upperAlarmLimit;
        private final Double upperCtrlLimit;
        private final Double upperDisplayLimit;
        
        SVStatistics(VStatistics v) {
            average = v.getAverage();
            stdDev = v.getStdDev();
            min = v.getMin();
            max = v.getMax();
            nSamples = v.getNSamples();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
            
            lowerDisplayLimit = v.getLowerDisplayLimit();
            lowerCtrlLimit = v.getLowerCtrlLimit();
            lowerAlarmLimit = v.getLowerAlarmLimit();
            lowerWarningLimit = v.getLowerWarningLimit();
            units = v.getUnits();
            format = v.getFormat();
            upperWarningLimit = v.getUpperWarningLimit();
            upperAlarmLimit = v.getUpperAlarmLimit();
            upperCtrlLimit = v.getUpperCtrlLimit();
            upperDisplayLimit = v.getUpperDisplayLimit();
        }
      
        @Override
        public VStatistics toVType() {
            return ValueFactory.newVStatistics(
                    average, 
                    stdDev,
                    min,
                    max,
                    nSamples,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid),
                    ValueFactory.newDisplay(lowerDisplayLimit, lowerAlarmLimit, lowerWarningLimit, 
                            units, format, upperWarningLimit, 
                            upperAlarmLimit, upperDisplayLimit, 
                            lowerCtrlLimit, upperCtrlLimit)
            );            
        }
        
    }
    
    class SVString implements SVType<VString> {
        
        private final String value;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        SVString(VString v) {
            value = v.getValue();
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
        }
        
        @Override
        public VString toVType() {
            return ValueFactory.newVString(
                    value,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid)
            );
        }
    }
    class SVStringArray implements SVType<VStringArray> {
        
        private final List<String> value;
        private final ListInt sizes;
        
        private final AlarmSeverity severity;
        private final String alarmName;
        
        private final Timestamp timestamp;
        private final Integer timeUserTag;
        private final boolean timeIsValid;
        
        
        SVStringArray(VStringArray v) {
            value = v.getData();
            sizes = v.getSizes();
            
            severity = v.getAlarmSeverity();
            alarmName = v.getAlarmName();
            
            timestamp = v.getTimestamp();
            timeUserTag = v.getTimeUserTag();
            timeIsValid = v.isTimeValid();
        }
         
        @Override
        public VStringArray toVType() {
            return ValueFactory.newVStringArray(
                    value,
                    ValueFactory.newAlarm(severity, alarmName),
                    ValueFactory.newTime(timestamp, timeUserTag, timeIsValid)
            );            
        }
        
    }
    
    class SVTable implements SVType<VTable> {
        private final int colCount;
        private final int rowCount;
        private final List<Class<?>> colTypes;
        private final List<String> colNames;
        private final List<Object> colDatum;
        
        SVTable(VTable v){
            colCount = v.getColumnCount();
            rowCount = v.getRowCount();
            
            colTypes = new ArrayList<>();
            colNames = new ArrayList<>();
            colDatum = new ArrayList<>();
            for (int i = 0; i < colCount; ++i){
                colTypes.add(v.getColumnType(i));
                colNames.add(v.getColumnName(i));
                colDatum.add(v.getColumnData(i));
            }
        }
        
        @Override
        public VTable toVType() {
            return ValueFactory.newVTable(colTypes, colNames, colDatum);
        }
        
    }
    
    public String serialize(VType v){
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .serializeSpecialFloatingPointValues()
                .create();

        if (v instanceof VBoolean){
            return gson.toJson(new SVBoolean((VBoolean)v));
        }
        
        if (v instanceof VByte){
            return gson.toJson(new SVByte((VByte)v));
        }
        if (v instanceof VByteArray){
            return gson.toJson(new SVByteArray((VByteArray)v));
        }        
        
        if (v instanceof VDouble){
            return gson.toJson(new SVDouble((VDouble)v));
        }
        if (v instanceof VDoubleArray){
            return gson.toJson(new SVDoubleArray((VDoubleArray)v));
        }         
        
        if (v instanceof VEnum){
            return gson.toJson(new SVEnum((VEnum)v));
        }
        if (v instanceof VEnumArray){
            return gson.toJson(new SVEnumArray((VEnumArray)v));
        } 
        
        if (v instanceof VExtendedAlarm){
            return gson.toJson(new SVExtendedAlarm((VExtendedAlarm)v));
        }
        
        if (v instanceof VFloat){
            return gson.toJson(new SVFloat((VFloat)v));
        }
        if (v instanceof VFloatArray){
            return gson.toJson(new SVFloatArray((VFloatArray)v));
        }         
        
        if (v instanceof VImage){
            return gson.toJson(new SVImage((VImage)v));
        }
        
        if (v instanceof VInt){
            return gson.toJson(new SVInt((VInt)v));
        }
        if (v instanceof VIntArray){
            return gson.toJson(new SVIntArray((VIntArray)v));
        }         
        
        if (v instanceof VLong){
            return gson.toJson(new SVLong((VLong)v));
        }
        if (v instanceof VLongArray){
            return gson.toJson(new SVLongArray((VLongArray)v));
        }         
        
        if (v instanceof VMultiDouble){
            return gson.toJson(new SVMultiDouble((VMultiDouble)v));
        }
        if (v instanceof VMultiEnum){
            return gson.toJson(new SVMultiEnum((VMultiEnum)v));
        }
        if (v instanceof VMultiInt){
            return gson.toJson(new SVMultiInt((VMultiInt)v));
        }
        if (v instanceof VMultiString){
            return gson.toJson(new SVMultiString((VMultiString)v));
        }        
        
        if (v instanceof VNumber){
            return gson.toJson(new SVNumber((VNumber)v));
        }
        if (v instanceof VNumberArray){
            return gson.toJson(new SVNumberArray((VNumberArray)v));
        } 
        
        if (v instanceof VShort){
            return gson.toJson(new SVShort((VShort)v));
        }
        if (v instanceof VShortArray){
            return gson.toJson(new SVShortArray((VShortArray)v));
        } 
        
        if (v instanceof VStatistics){
            return gson.toJson(new SVStatistics((VStatistics)v));
        }
        
        if (v instanceof VString){
            return gson.toJson(new SVString((VString)v));
        }
        if (v instanceof VStringArray){
            return gson.toJson(new SVStringArray((VStringArray)v));
        } 
        
        if (v instanceof VTable){
            return gson.toJson(new SVTable((VTable)v));
        }
        
        throw new IllegalArgumentException(v.getClass() + " is not a supported type.");
    }
    public void export(FileWriter writer, VType v){
        try {
            writer.append(serialize(v));
        } catch (IOException ex) {
            throw new RuntimeException("Filewrite failure", ex);
        }
    }

    
/*
Class           Serialized      Tested
--------------------------------------
boolean         Y
byte            Y
bytearray       Y
double          Y
doublearray     Y
enum            Y
enumarray       Y
extendedalarm   Y
float           Y
floatarray      Y
image           Y
int             Y
intarray        Y
long            Y
longarray       Y
multidouble     Y
multienum       Y
multiint        Y
multistring     Y
number          Y
numerarray      Y
short           Y
shortarray      Y
statistics      Y
string          Y
stringarray     Y
table           Y
    
    //deserialize
    
    //everything with an object field might be invalid
    //  dimensions, format, timestamp
    //  @table, statistics, alarm
    
    //test
    //include version and class
    
*/
    public void test(){
        System.out.println(serialize(makeByteArray()));
    }
    
    public static VBoolean makeBoolean() {
        return ValueFactory.newVBoolean(
                true,
                ValueFactory.newAlarm(AlarmSeverity.NONE, "Alarm"),
                ValueFactory.newTime(Timestamp.now(), 0, true)
        );
    }    
    public static VDouble makeDouble() {
        return ValueFactory.newVDouble(
                2.0,
                ValueFactory.newAlarm(AlarmSeverity.NONE, "Alarm"),
                ValueFactory.newTime(Timestamp.now(), 0, true),
                ValueFactory.displayBoolean()
        );
    }        
    public static VByteArray makeByteArray(){
        return (VByteArray) newVNumberArray(
                new ArrayByte(new byte[] {3,5,2,4,1}),
                new ArrayInt(5), 
                Arrays.asList(newDisplay(new ArrayDouble(0, 0.5, 1, 1.5, 2, 2.5), "m")),
                newAlarm(AlarmSeverity.MINOR, "LOW"), 
                newTime(Timestamp.of(1354719441, 521786982)), 
                ValueFactory.displayNone()
        );        
    }
    public static VMultiDouble makeMultiDouble(){
        return ValueFactory.newVMultiDouble(
                Arrays.asList(makeDouble(), makeDouble()),
                newAlarm(AlarmSeverity.MINOR, "LOW"), 
                newTime(Timestamp.of(1354719441, 521786982)), 
                ValueFactory.displayNone()
        );         
    }
    public static void main(String[] args) {
        JsonIO tmp = new JsonIO();
        tmp.test();
    }
}
