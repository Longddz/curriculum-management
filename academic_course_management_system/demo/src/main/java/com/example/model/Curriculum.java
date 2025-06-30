package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "curriculum")
public class Curriculum {

    @Id
    @Column(name = "curriculum_code", length = 20, nullable = false)
    @NotBlank(message = "Curriculum code is required")
    @Size(max = 20, message = "Curriculum code must not exceed 20 characters")
    private String curriculumCode;

    @Column(name = "identifier", length = 50)
    @Size(max = 50, message = "Identifier must not exceed 50 characters")
    private String identifier;

    @Column(name = "name_curriculum", length = 100)
    @Size(max = 100, message = "Name curriculum must not exceed 100 characters")
    private String nameCurriculum;

    @Column(name = "lecturer", length = 100)
    @Size(max = 100, message = "Lecturer must not exceed 100 characters")
    private String lecturer;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "url", length = 255)
    @Size(max = 255, message = "URL must not exceed 255 characters")
    private String url;

    @Column(name = "subject", length = 100)
    @Size(max = 100, message = "Subject must not exceed 100 characters")
    private String subject;

    @Column(name = "branch", length = 100)
    @Size(max = 100, message = "Branch must not exceed 100 characters")
    private String branch;

    @Column(name = "department", length = 100)
    @Size(max = 100, message = "Department must not exceed 100 characters")
    private String department;

    @Column(name = "subject_code", length = 50)
    @Size(max = 50, message = "Subject code must not exceed 50 characters")
    private String subjectCode;

    @Column(name = "branch_code", length = 50)
    @Size(max = 50, message = "Branch code must not exceed 50 characters")
    private String branchCode;

    @Column(name = "department_code", length = 50)
    @Size(max = 50, message = "Department code must not exceed 50 characters")
    private String departmentCode;

    @ManyToOne
    @JoinColumn(name = "identifier", referencedColumnName = "identifier", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "FK_curriculum_user",
                    foreignKeyDefinition = "FOREIGN KEY (identifier) REFERENCES User(identifier) ON DELETE SET NULL ON UPDATE CASCADE"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "subject_code", referencedColumnName = "subject_code", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "FK_curriculum_subject",
                    foreignKeyDefinition = "FOREIGN KEY (subject_code) REFERENCES Subject(subject_code) ON DELETE SET NULL ON UPDATE CASCADE"))
    private Subject subjectEntity;

    @ManyToOne
    @JoinColumn(name = "branch_code", referencedColumnName = "branch_code", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "FK_curriculum_branch",
                    foreignKeyDefinition = "FOREIGN KEY (branch_code) REFERENCES Branch(branch_code) ON DELETE SET NULL ON UPDATE CASCADE"))
    private Branch branchEntity;

    @ManyToOne
    @JoinColumn(name = "department_code", referencedColumnName = "department_code", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "FK_curriculum_school",
                    foreignKeyDefinition = "FOREIGN KEY (department_code) REFERENCES School(department_code) ON DELETE SET NULL ON UPDATE CASCADE"))
    private School school;
}