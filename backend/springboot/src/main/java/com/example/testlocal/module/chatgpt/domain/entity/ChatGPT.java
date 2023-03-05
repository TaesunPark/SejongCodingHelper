package com.example.testlocal.module.chatgpt.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "chatgpt")
public class ChatGPT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // chat id
}
