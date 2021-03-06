package com.prototype.restdocopenapi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EntityListeners(FooListener.class)
@Entity
@Getter 
@Setter 
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Foo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    
    @Column()
    private String body;

    @Column()
    private String metadata;
    
    @Column()
    private String name;
    
    @Column()
    private String link;
    
    public Foo(long id, String title, String body, String metadata, String name, String link) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.metadata = metadata;
        this.name = name;
        this.link = link;
    }
}
