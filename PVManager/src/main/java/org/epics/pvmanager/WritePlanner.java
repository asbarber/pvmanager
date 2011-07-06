/*
 * Copyright 2008-2011 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */
package org.epics.pvmanager;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.epics.pvmanager.WriteBuffer;
import org.epics.pvmanager.WriteCache;

/**
 *
 * @author carcassi
 */
class WritePlanner {
    
    private Map<String, ChannelHandler> channels = new HashMap<String, ChannelHandler>();
    private Map<String, Object> values = new HashMap<String, Object>();
    private Map<String, Set<String>> preceding = new HashMap<String, Set<String>>();
    private Map<String, Set<String>> succeeding = new HashMap<String, Set<String>>();
    private Set<String> leafs = new HashSet<String>();
    
    void addChannel(ChannelHandler channel, Object value, Collection<String> precedingChannels) {
        channels.put(channel.getChannelName(), channel);
        values.put(channel.getChannelName(), value);
        preceding.put(channel.getChannelName(), new HashSet<String>(precedingChannels));
        for (String precedingChannel : precedingChannels) {
            Set<String> succeedingChannels = succeeding.get(precedingChannel);
            if (succeedingChannels ==  null) {
                succeedingChannels = new HashSet<String>();
                succeeding.put(precedingChannel, succeedingChannels);
            }
            succeedingChannels.add(channel.getChannelName());
        }
        if (precedingChannels.isEmpty()) {
            leafs.add(channel.getChannelName());
        }
    }
    
    void removeChannel(String channelName) {
        channels.remove(channelName);
        values.remove(channelName);
        preceding.remove(channelName);
        Set<String> succeedingChannels = succeeding.remove(channelName);
        for (String succeedingChannel : succeedingChannels) {
            Set<String> precedingChannels = preceding.get(succeedingChannel);
            precedingChannels.remove(channelName);
            if (precedingChannels.isEmpty())
                leafs.add(succeedingChannel);
        }
    }

    Map<ChannelHandler, Object> nextChannels() {
        Map<ChannelHandler, Object> nextChannels = new HashMap<ChannelHandler, Object>();
        for (String channelName : leafs) {
            nextChannels.put(channels.get(channelName), values.get(channelName));
        }
        leafs.clear();
        return nextChannels;
    }
    
}