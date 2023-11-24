package com.programming.man.mdchat.model;

import com.programming.man.mdchat.exceptions.SpringMDChatException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),
    ;

    private int direction;

    VoteType(int direction) {
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new SpringMDChatException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
