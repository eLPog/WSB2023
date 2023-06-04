package wsb2023.pogorzelski.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import wsb2023.pogorzelski.models.*;
import wsb2023.pogorzelski.repositories.IssueRepository;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    final private  ProjectService projectService;
    final private UtilService utilService;
    final private PersonService personService;

    final private IssueRepository issueRepository;

public Page<Issue> findAll(Specification<Issue> specification, Pageable pageable){
    return issueRepository.findAll(specification,pageable);
}

    public void addIssueToProject(IssueAddObject newIssue) {
        Person person = personService.getLoggedUser();

        Issue issue = new Issue();
        issue.setProject(projectService.findProjectById( Long.parseLong(newIssue.getProjectId())));
        issue.setStatus(newIssue.getStatus());
        issue.setPriority(newIssue.getPriority());
        issue.setType(newIssue.getType());
        issue.setName(newIssue.getName());
        issue.setDescription(newIssue.getDescription());
        issue.setCreator(person);
        issue.setDateCreated(new Date());
        issue.setLastUpdate(new Date());
        issueRepository.save(issue);

    }

    public void editIssue(IssueEditObject editedIssue, Long issueId){
        Issue issue = issueRepository.findById(issueId).orElseThrow();
        issue.setName(editedIssue.getName());
        issue.setDescription(editedIssue.getDescription());
        issue.setType(editedIssue.getType());
        issue.setPriority(editedIssue.getPriority());
        issue.setStatus(editedIssue.getStatus());
        issueRepository.save(issue);
    }

    public List<Issue> allIssuesForProject(Long projectId){
            return issueRepository.findAllByProjectId(projectId);
    }

    public Issue findById(Long issueId){
        return issueRepository.findById(issueId).orElseThrow();
    }

    public void assignUserToIssue(Long issueId){
        Issue issue = findById(issueId);
        Person person = personService.getLoggedUser();
        issue.setAssignee(person);
        issueRepository.save(issue);


    }

    public void deleteIssue(Long issueId){

        issueRepository.deleteById(issueId);
    }


}
