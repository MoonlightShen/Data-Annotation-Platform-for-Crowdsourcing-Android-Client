package com.echo.datatag3.bean.entity;


import com.echo.datatag3.bean.converter.conmon.LongListConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

@Entity(nameInDb = "dt_team")
public class Team {
    private Long teamId;
    private Long ownerId;
    private String name;
    private String description;
    @Convert(converter = LongListConverter.class, columnType = String.class)
    private List<Long> administrators;
    @Convert(converter = LongListConverter.class, columnType = String.class)
    private List<Long> members;
    private String lastMessage;
    private Long lastMessageTime;





    @Generated(hash = 690204350)
    public Team(Long teamId, Long ownerId, String name, String description,
            List<Long> administrators, List<Long> members, String lastMessage,
            Long lastMessageTime) {
        this.teamId = teamId;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.administrators = administrators;
        this.members = members;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    @Generated(hash = 882286361)
    public Team() {
    }





    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<Long> administrators) {
        this.administrators = administrators;
    }

    public List<Long> getMembers() {
        return members;
    }

    public void setMembers(List<Long> members) {
        this.members = members;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Long getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
