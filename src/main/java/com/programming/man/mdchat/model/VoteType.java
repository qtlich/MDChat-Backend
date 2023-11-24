package com.programming.man.mdchat.model;

import com.programming.man.mdchat.exceptions.SpringMDChatException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE_POST(1),
    DOWNVOTE_POST(-1),
    UPVOTE_COMMENT(1),
    DOWNVOTE_COMMENT(-1);

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
