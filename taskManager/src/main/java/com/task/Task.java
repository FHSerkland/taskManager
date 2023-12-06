package com.task;

import java.sql.Date;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Task {
	private @Id Long id;
	private String title;
	private String description;
	private Date dueDate;
	private Date completedDate;
	private Long percentCompleted;
	private String userName;
	private String priority;
}
