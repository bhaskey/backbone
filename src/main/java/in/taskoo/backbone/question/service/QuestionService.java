package in.taskoo.backbone.question.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import in.taskoo.backbone.common.dto.CreateResponse;
import in.taskoo.backbone.question.dto.Question;
import in.taskoo.backbone.question.entity.QuestionEntity;
import in.taskoo.backbone.question.repository.QuestionRepository;
import in.taskoo.backbone.task.entity.TaskEntity;
import in.taskoo.backbone.task.repository.TaskRepository;
import in.taskoo.backbone.user.entity.UserEntity;
import in.taskoo.backbone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

  private final QuestionRepository repository;

  private final UserRepository userRepository;
  private final TaskRepository taskRepository;

  public CreateResponse ask(@Valid Question question) {
    UserEntity userEntity = userRepository.findById(question.getUser().getId())
        .orElseThrow(null);
    TaskEntity taskEntity = taskRepository.findById(question.getTaskId())
        .orElseThrow(null);
    QuestionEntity parent = null;
    if (question.getParentId().isPresent()) {
      parent = repository.findById(question.getParentId().get()).orElse(null);
    }
    QuestionEntity questionEntity = repository.save(
        new QuestionEntity()
        .setTaskEntity(taskEntity)
        .setUserEntity(userEntity)
        .setParent(parent)
        .setQuestionText(question.getQuestionText()));
    return new CreateResponse()
        .setId(questionEntity.getId());
  }

}
