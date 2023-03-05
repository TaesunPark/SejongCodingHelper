package com.example.testlocal.module.chatgpt.domain.entity;

import com.example.testlocal.config.DateTime;
import com.example.testlocal.module.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "user_chatgpt_message")
public class UserChatGPTMessage extends DateTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // chat id

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "chatgpt_id", nullable = false)
    private ChatGPT chatGPT;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false)
    private String answer;

    public UserChatGPTMessage(User user, ChatGPT chatGPT, String question, String answer) {
        this.user = user;
        this.chatGPT = chatGPT;
        this.answer = answer;
        this.question = question;
    }
}
