package in.taskoo.backbone.task.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import in.taskoo.backbone.common.dto.CreateResponse;
import in.taskoo.backbone.offer.mapper.OfferMapper;
import in.taskoo.backbone.question.mapper.QuestionMapper;
import in.taskoo.backbone.task.dto.Task;
import in.taskoo.backbone.task.dto.TaskLite;
import in.taskoo.backbone.task.entity.TaskEntity;
import in.taskoo.backbone.task.mapper.TaskMapper;
import in.taskoo.backbone.task.repository.TaskRepository;
import in.taskoo.backbone.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TaskService {

  private final TaskMapper taskMapper;
  private final TaskRepository taskRepository;

  private final OfferMapper offerMapper;
  private final UserService userService;
  private final QuestionMapper questionMapper;


  @Transactional
  public CreateResponse postTask(@Valid TaskLite task) {
    TaskEntity taskEntity = taskRepository
        .save(taskMapper.toEntity(task).setUserEntity(userService.findOrCreateNew(task.getUser())));
    return new CreateResponse().setId(taskEntity.getId());
  }

  public TaskLite getTaskLite(Long taskId) {
    TaskEntity taskEntity = taskRepository.findById(taskId)
        .orElseThrow(null);
    return taskMapper.toTask(taskEntity);
  }

  @Transactional
  public Task getTask(Long taskId) {
    TaskEntity taskEntity = taskRepository.findById(taskId)
        .orElseThrow(null);
    Hibernate.initialize(taskEntity.getOffers());
    Hibernate.initialize(taskEntity.getQuestions());
    return new Task()
        .setTaskLite(taskMapper.toTask(taskEntity))
        .setOffers(offerMapper.toOffers(taskEntity.getOffers()))
        .setQuestions(questionMapper.toQuestions(taskEntity.getQuestions()));
  }

  public List<TaskLite> search(String query, String loc, Integer maxp, Integer minp, Integer tt, Integer ts) {
    // TODO replaces with search service
    return taskMapper.toTaskLites(taskRepository.search(query, loc, maxp, minp, tt, ts));
  }

}
