package com.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.repository.TaskRepository;
import com.task.Task;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {
	@Autowired
	private TaskRepository taskRepository;
	
	@PostMapping("/create")
	public Task createTask(@RequestBody Task newTask) {
		return taskRepository.save(newTask);
	}
	
	@PutMapping("/api/tasks/{taskId}")
	public Task putTask(@RequestBody Task task, @PathVariable Integer id) {
		return taskRepository.findById(id)
			      .map(task -> {
			        task.setName(task.getTitle());
			        task.setRole(task.getDescription());
			        task.setDueDate(task.getDueDate());
			        task.setCompletedDate(task.getCompletedDate());
			        task.setPercentCompleted(task.getPercentCompleted());
			        task.setPriority(task.getPriority());
			        return taskRepository.save(task);
			      })
			      .orElseGet(() -> {
			        task.setId(id);
			        return taskRepository.save(task);
			      });
	}
	
	@GetMapping("/api/tasks")
	public List<Task> getAllTasks() {
		return taskRepository.getAll();
	}
	
	@PostMapping("/api/tasks/{taskId}/assign")
	public Task assignTask(@RequestBody Task task, @PathVariable Integer id,
						   @PathVariable String userName) {
		return taskRepository.findById(id)
			      .map(task -> {
			        task.setName(task.getTitle());
			        task.setRole(task.getDescription());
			        task.setDueDate(task.getDueDate());
			        task.setCompletedDate(task.getCompletedDate());
			        task.setPercentCompleted(task.getPercentCompleted());
			        task.setPriority(task.getPriority());
			        task.setUserName(userName);
			        return taskRepository.save(task);
			      })
			      .orElseGet(() -> {
			        task.setId(id);
			        return taskRepository.save(task);
			      });
	}
	
	@GetMapping("/api/users/{userId}/tasks")
	public List<Task> getTasks(@RequestBody Task task, @PathVariable String userName) {
		return taskRepository.getAll()
				.stream()
				.filter(task.getName().equals(userName)
				.collect(Collectors.toList());
	}
	
	@PutMapping("/api/tasks/{taskId}/progress")
	public Task setProgress(@RequestBody Task task,  @PathVariable Long id,
							Long percentCompleted) {
		return taskRepository.findById(id)
			      .map(task -> {
			        task.setName(task.getTitle());
			        task.setRole(task.getDescription());
			        task.setDueDate(task.getDueDate());
			        task.setCompletedDate(task.getCompletedDate());
			        task.setPercentCompleted(percentCompleted);
			        task.setPriority(task.getPriority());
			        task.setUserName(task.getUserName);
			        return taskRepository.save(task);
			      })
			      .orElseGet(() -> {
			        task.setId(id);
			        return taskRepository.save(task);
			      });	
	}
	
	@GetMapping("/api/tasks/overdue")
	public List<Task> getOverdueTasks() {
		Date currentDate = new Date(System.currentTimeMillis())
		return taskRepository.getAll()
				.stream()
				.filter(task -> task.getDueDate() < currentDate)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/api/tasks/status/{status}")
	public List<Task> getTasksWithSpecificStatus(@PathVariable String status) {
		Date currentDate = new Date(System.currentTimeMillis());
		return taskRepository.getAll()
				.stream()
				.filter(task -> task.getStatus().equals(status))
				.collect(Collectors.toList());
	}
	
	@GetMapping("/api/tasks/completed")
	public List<Task> getCompletedTasks(@PathVariable Date startDate,
										@PathVariable Date endDate) {
		Date currentDate = new Date(System.currentTimeMillis());
		return taskRepository.getAll()
				.stream()
				.filter(task -> task.getCompletedDate() >= startDate
								&& task.getCompletedDate() <= endDate
								&& task.getPercentCompleted() == 100)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/api/tasks/statistics")
	public HashMap<String, Integer> getCompletedTasks() {
		List<Task> tasks = taskRepository.getAll();
		HashMap<String, Integer> statistics = new HashMap<>();
		statistics.put("Total tasks", tasks.size());
		int completedTasks = tasks.stream()
							.filter(task -> task.getPercentCompleted() == 100)
							.collect(Collectors.toList())
							.size();
		statistics.put("Completed tasks", completedTasks);
		statistics.put("Completed tasks", 
						(int) ((double) completedTasks / (double) tasks.size()));
		return statistics;
	}
	
	@GetMapping("/api/tasks/priorityQueue/{priority}")
	public PriorityQueue<Task> priorityQueue(@PathVariable String priority) {
		PriorityQueue<Task> pq = new PriorityQueue<>();
		List<Task> tasksByPriority = taskRepository.getAll()
												   .stream()
												   .filter(task -> task.getPriority().equals(priority))
												   .collect(Collectors.toList());
		for (Task task : tasksByPriority) {
			pq.add(task);
		}
		
		return pq;
	}
}