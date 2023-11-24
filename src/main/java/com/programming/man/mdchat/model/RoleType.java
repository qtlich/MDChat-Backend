package com.programming.man.mdchat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/*
RoleTypeID	RoleName	Description
1	User	Обычный пользователь
2	Moderator	Модератор подраздела
3	Administrator	Администратор платформы
4	Supermoderator	Супермодератор (высший уровень модерации)
 */
@Getter
@Setter
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleTypeId;
    @NotNull
    private String roleName;
    @NotNull
    private String description;
}
