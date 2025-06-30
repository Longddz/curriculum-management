// package com.example.service;

// import com.example.dto.CurriculumDTO;
// import com.example.model.Curriculum;
// import com.example.repository.CurriculumRepository;
// import com.example.repository.UserRepository;
// import com.example.repository.SchoolRepository;
// import com.example.repository.BranchRepository;
// import com.example.repository.SubjectRepository;
// import jakarta.persistence.EntityNotFoundException;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.List;

// @Service
// public class CurriculumService {

//     private static final Logger logger = LoggerFactory.getLogger(CurriculumService.class);

//     @Autowired
//     private CurriculumRepository curriculumRepository;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private SubjectRepository subjectRepository;

//     @Autowired
//     private BranchRepository branchRepository;

//     @Autowired
//     private SchoolRepository schoolRepository;

//     @Transactional
//     public Curriculum addCurriculum(CurriculumDTO dto) {
//         logger.info("Adding curriculum with code: {}", dto.getCurriculumCode());

//         if (curriculumRepository.existsByCurriculumCode(dto.getCurriculumCode())) {
//             throw new IllegalArgumentException("Curriculum with code " + dto.getCurriculumCode() + " already exists");
//         }

//         validateForeignKeys(dto);

//         Curriculum curriculum = Curriculum.builder()
//                 .curriculumCode(dto.getCurriculumCode())
//                 .identifier(dto.getIdentifier())
//                 .nameCurriculum(dto.getNameCurriculum())
//                 .lecturer(dto.getLecturer())
//                 .description(dto.getDescription())
//                 .url(dto.getUrl())
//                 .subject(dto.getSubject())
//                 .branch(dto.getBranch())
//                 .department(dto.getDepartment())
//                 .subjectCode(dto.getSubjectCode())
//                 .branchCode(dto.getBranchCode())
//                 .departmentCode(dto.getDepartmentCode())
//                 .build();

//         Curriculum saved = curriculumRepository.save(curriculum);
//         logger.info("Curriculum added successfully: {}", saved.getCurriculumCode());
//         return saved;
//     }

//     @Transactional
//     public Curriculum updateCurriculum(String code, CurriculumDTO dto) {
//         logger.info("Updating curriculum with code: {}", code);

//         Curriculum existing = curriculumRepository.findByCurriculumCode(code);
//         if (existing == null) {
//             throw new EntityNotFoundException("Curriculum with code " + code + " not found");
//         }

//         validateForeignKeys(dto);

//         existing.setIdentifier(dto.getIdentifier());
//         existing.setNameCurriculum(dto.getNameCurriculum());
//         existing.setLecturer(dto.getLecturer());
//         existing.setDescription(dto.getDescription());
//         existing.setUrl(dto.getUrl());
//         existing.setSubject(dto.getSubject());
//         existing.setBranch(dto.getBranch());
//         existing.setDepartment(dto.getDepartment());
//         existing.setSubjectCode(dto.getSubjectCode());
//         existing.setBranchCode(dto.getBranchCode());
//         existing.setDepartmentCode(dto.getDepartmentCode());

//         Curriculum updated = curriculumRepository.save(existing);
//         logger.info("Curriculum updated successfully: {}", updated.getCurriculumCode());
//         return updated;
//     }

//     @Transactional
//     public boolean deleteCurriculum(String code) {
//         logger.info("Deleting curriculum with code: {}", code);

//         if (!curriculumRepository.existsByCurriculumCode(code)) {
//             throw new EntityNotFoundException("Curriculum with code " + code + " not found");
//         }
//         curriculumRepository.deleteById(code);
//         logger.info("Curriculum deleted successfully: {}", code);
//         return true;
//     }

//     public List<Curriculum> getAllCurriculums() {
//         logger.info("Retrieving all curriculums");
//         return curriculumRepository.findAll();
//     }

//     public List<Curriculum> filterCurriculums(String name, String lecturer, String identifier, String subject, String branch, String department) {
//         logger.info("Filtering curriculums with criteria: name={}, lecturer={}, identifier={}, subject={}, branch={}, department={}",
//                 name, lecturer, identifier, subject, branch, department);
//         return curriculumRepository.findByNameCurriculumContainingIgnoreCaseAndLecturerContainingIgnoreCaseAndIdentifierContainingIgnoreCaseAndSubjectContainingIgnoreCaseAndBranchContainingIgnoreCaseAndDepartmentContainingIgnoreCase(
//                 name != null ? name : "",
//                 lecturer != null ? lecturer : "",
//                 identifier != null ? identifier : "",
//                 subject != null ? subject : "",
//                 branch != null ? branch : "",
//                 department != null ? department : "");
//     }

//     public Curriculum getCurriculumByCode(String code) {
//         logger.info("Retrieving curriculum with code: {}", code);
//         Curriculum curriculum = curriculumRepository.findByCurriculumCode(code);
//         if (curriculum == null) {
//             throw new EntityNotFoundException("Curriculum with code " + code + " not found");
//         }
//         logger.info("Curriculum retrieved successfully: {}", code);
//         return curriculum;
//     }

//     private void validateForeignKeys(CurriculumDTO dto) {
//         if (dto.getIdentifier() != null && !userRepository.existsByIdentifier(dto.getIdentifier())) {
//             throw new EntityNotFoundException("User with identifier " + dto.getIdentifier() + " not found");
//         }
//         if (dto.getSubjectCode() != null && !subjectRepository.existsBySubjectCode(dto.getSubjectCode())) {
//             throw new EntityNotFoundException("Subject with code " + dto.getSubjectCode() + " not found");
//         }
//         if (dto.getBranchCode() != null && !branchRepository.existsByBranchCode(dto.getBranchCode())) {
//             throw new EntityNotFoundException("Branch with code " + dto.getBranchCode() + " not found");
//         }
//         if (dto.getDepartmentCode() != null && !schoolRepository.existsByDepartmentCode(dto.getDepartmentCode())) {
//             throw new EntityNotFoundException("School with department code " + dto.getDepartmentCode() + " not found");
//         }
//     }
// }


/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

// package com.example.service;

// import com.cloudinary.Cloudinary;
// import com.cloudinary.utils.ObjectUtils;
// import com.example.dto.CurriculumDTO;
// import com.example.model.Curriculum;
// import com.example.repository.BranchRepository;
// import com.example.repository.CurriculumRepository;
// import com.example.repository.SchoolRepository;
// import com.example.repository.SubjectRepository;
// import com.example.repository.UserRepository;
// import jakarta.persistence.EntityNotFoundException;
// import lombok.RequiredArgsConstructor;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.IOException;
// import java.util.List;
// import java.util.Map;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

// @Service
// @RequiredArgsConstructor
// public class CurriculumService {

//     private static final Logger logger = LoggerFactory.getLogger(CurriculumService.class);
//     private static final List<String> ALLOWED_FILE_TYPES = List.of("application/pdf");
//     private static final long MAX_FILE_SIZE = 20 * 1024 * 1024; // 20MB

//     private final CurriculumRepository curriculumRepository;
//     private final UserRepository userRepository;
//     private final SubjectRepository subjectRepository;
//     private final BranchRepository branchRepository;
//     private final SchoolRepository schoolRepository;
//     private final Cloudinary cloudinary;

//     @Transactional
//     public Curriculum addCurriculum(CurriculumDTO dto, MultipartFile file) {
//         logger.info("Adding curriculum with code: {}", dto.getCurriculumCode());

//         if (curriculumRepository.existsByCurriculumCode(dto.getCurriculumCode())) {
//             throw new IllegalArgumentException("Curriculum with code " + dto.getCurriculumCode() + " already exists");
//         }

//         validateForeignKeys(dto);
//         String uploadedUrl = uploadFileToCloudinary(file);

//         Curriculum curriculum = Curriculum.builder()
//                 .curriculumCode(dto.getCurriculumCode())
//                 .identifier(dto.getIdentifier())
//                 .nameCurriculum(dto.getNameCurriculum())
//                 .lecturer(dto.getLecturer())
//                 .description(dto.getDescription())
//                 .url(uploadedUrl)
//                 .subject(dto.getSubject())
//                 .branch(dto.getBranch())
//                 .department(dto.getDepartment())
//                 .subjectCode(dto.getSubjectCode())
//                 .branchCode(dto.getBranchCode())
//                 .departmentCode(dto.getDepartmentCode())
//                 .build();

//         Curriculum saved = curriculumRepository.save(curriculum);
//         logger.info("Curriculum added successfully: {}", saved.getCurriculumCode());
//         return saved;
//     }

//     @Transactional
//     public Curriculum updateCurriculum(String code, CurriculumDTO dto, MultipartFile file) {
//         logger.info("Updating curriculum with code: {}", code);

//         Curriculum existing = curriculumRepository.findByCurriculumCode(code);
//         if (existing == null) {
//             throw new EntityNotFoundException("Curriculum with code " + code + " not found");
//         }

//         validateForeignKeys(dto);
//         String uploadedUrl = uploadFileToCloudinary(file);
//         if (uploadedUrl != null) {
//             // Delete old file if it exists
//             if (existing.getUrl() != null) {
//                 deleteFileFromCloudinary(existing.getUrl());
//             }
//             existing.setUrl(uploadedUrl);
//         }

//         existing.setIdentifier(dto.getIdentifier());
//         existing.setNameCurriculum(dto.getNameCurriculum());
//         existing.setLecturer(dto.getLecturer());
//         existing.setDescription(dto.getDescription());
//         existing.setSubject(dto.getSubject());
//         existing.setBranch(dto.getBranch());
//         existing.setDepartment(dto.getDepartment());
//         existing.setSubjectCode(dto.getSubjectCode());
//         existing.setBranchCode(dto.getBranchCode());
//         existing.setDepartmentCode(dto.getDepartmentCode());

//         Curriculum updated = curriculumRepository.save(existing);
//         logger.info("Curriculum updated successfully: {}", updated.getCurriculumCode());
//         return updated;
//     }

//     @Transactional
//     public void deleteCurriculum(String code) {
//         logger.info("Deleting curriculum with code: {}", code);

//         Curriculum curriculum = curriculumRepository.findByCurriculumCode(code);
//         if (curriculum == null) {
//             throw new EntityNotFoundException("Curriculum with code " + code + " not found");
//         }

//         if (curriculum.getUrl() != null) {
//             deleteFileFromCloudinary(curriculum.getUrl());
//         }

//         curriculumRepository.deleteById(code);
//         logger.info("Curriculum deleted successfully: {}", code);
//     }

//     public List<Curriculum> getAllCurriculums() {
//         logger.info("Retrieving all curriculums");
//         return curriculumRepository.findAll();
//     }

//     public List<Curriculum> filterCurriculums(String name, String lecturer, String identifier, String subject, String branch, String department) {
//         logger.info("Filtering curriculums with criteria");
//         return curriculumRepository.findByNameCurriculumContainingIgnoreCaseAndLecturerContainingIgnoreCaseAndIdentifierContainingIgnoreCaseAndSubjectContainingIgnoreCaseAndBranchContainingIgnoreCaseAndDepartmentContainingIgnoreCase(
//                 name != null ? name : "",
//                 lecturer != null ? lecturer : "",
//                 identifier != null ? identifier : "",
//                 subject != null ? subject : "",
//                 branch != null ? branch : "",
//                 department != null ? department : "");
//     }

//     public Curriculum getCurriculumByCode(String code) {
//         logger.info("Retrieving curriculum with code: {}", code);
//         Curriculum curriculum = curriculumRepository.findByCurriculumCode(code);
//         if (curriculum == null) {
//             throw new EntityNotFoundException("Curriculum with code " + code + " not found");
//         }
//         return curriculum;
//     }

//     private void validateForeignKeys(CurriculumDTO dto) {
//         if (dto.getIdentifier() != null && !userRepository.existsByIdentifier(dto.getIdentifier())) {
//             throw new EntityNotFoundException("User with identifier " + dto.getIdentifier() + " not found");
//         }
//         if (dto.getSubjectCode() != null && !subjectRepository.existsBySubjectCode(dto.getSubjectCode())) {
//             throw new EntityNotFoundException("Subject with code " + dto.getSubjectCode() + " not found");
//         }
//         if (dto.getBranchCode() != null && !branchRepository.existsByBranchCode(dto.getBranchCode())) {
//             throw new EntityNotFoundException("Branch with code " + dto.getBranchCode() + " not found");
//         }
//         if (dto.getDepartmentCode() != null && !schoolRepository.existsByDepartmentCode(dto.getDepartmentCode())) {
//             throw new EntityNotFoundException("School with department code " + dto.getDepartmentCode() + " not found");
//         }
//     }

//     private String uploadFileToCloudinary(MultipartFile file) {
//         if (file == null || file.isEmpty()) {
//             return null;
//         }

//         if (!ALLOWED_FILE_TYPES.contains(file.getContentType())) {
//             throw new IllegalArgumentException("Only PDF files are allowed");
//         }
//         if (file.getSize() > MAX_FILE_SIZE) {
//             throw new IllegalArgumentException("File size exceeds 20MB limit");
//         }

//         try {
//             Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(),
//                     ObjectUtils.asMap("resource_type", "raw"));
//             return uploadResult.get("secure_url").toString();
//         } catch (IOException e) {
//             logger.error("Failed to upload file to Cloudinary: {}", e.getMessage());
//             throw new RuntimeException("Failed to upload file to Cloudinary", e);
//         }
//     }

//     private void deleteFileFromCloudinary(String url) {
//         try {
//             String publicId = extractPublicIdFromUrl(url);
//             cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "raw"));
//             logger.info("Deleted file from Cloudinary: {}", publicId);
//         } catch (IOException e) {
//             logger.warn("Failed to delete file from Cloudinary: {}", e.getMessage());
//         }
//     }

//     private String extractPublicIdFromUrl(String url) {
//         // Example URL: https://res.cloudinary.com/<cloud_name>/raw/upload/v1234567890/folder/filename.pdf
//         String regex = "https://res\\.cloudinary\\.com/[^/]+/raw/upload/v\\d+/(.+?)(\\.[^/.]+)?$";
//         Pattern pattern = Pattern.compile(regex);
//         Matcher matcher = pattern.matcher(url);
//         if (matcher.find()) {
//             return matcher.group(1); // Returns folder/filename without extension
//         }
//         throw new IllegalArgumentException("Invalid Cloudinary URL: " + url);
//     }
// }

package com.example.service;

import com.example.dto.CurriculumDTO;
import com.example.model.Curriculum;
import com.example.repository.BranchRepository;
import com.example.repository.CurriculumRepository;
import com.example.repository.SchoolRepository;
import com.example.repository.SubjectRepository;
import com.example.repository.UserRepository;
import com.example.util.CloudinaryUploader;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CurriculumService {

    private static final Logger logger = LoggerFactory.getLogger(CurriculumService.class);
    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;

    private final CurriculumRepository curriculumRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final BranchRepository branchRepository;
    private final SchoolRepository schoolRepository;
    private final CloudinaryUploader cloudinaryUploader;

    @Transactional
    public Curriculum addCurriculum(CurriculumDTO dto, MultipartFile file) {
        logger.info("Adding curriculum with code: {}", dto.getCurriculumCode());

        if (curriculumRepository.existsByCurriculumCode(dto.getCurriculumCode())) {
            throw new IllegalArgumentException("Curriculum with code " + dto.getCurriculumCode() + " already exists");
        }

        validateForeignKeys(dto);

        String uploadedUrl = file != null ? cloudinaryUploader.uploadFile(file) : null;

        Curriculum curriculum = Curriculum.builder()
                .curriculumCode(dto.getCurriculumCode())
                .identifier(dto.getIdentifier())
                .nameCurriculum(dto.getNameCurriculum())
                .lecturer(dto.getLecturer())
                .description(dto.getDescription())
                .url(uploadedUrl)
                .subject(dto.getSubject())
                .branch(dto.getBranch())
                .department(dto.getDepartment())
                .subjectCode(dto.getSubjectCode())
                .branchCode(dto.getBranchCode())
                .departmentCode(dto.getDepartmentCode())
                .build();

        return curriculumRepository.save(curriculum);
    }

    @Transactional
    public Curriculum updateCurriculum(String code, CurriculumDTO dto, MultipartFile file) {
        logger.info("Updating curriculum with code: {}", code);

        Curriculum existing = curriculumRepository.findByCurriculumCode(code);
        if (existing == null) {
            throw new EntityNotFoundException("Curriculum with code " + code + " not found");
        }

        validateForeignKeys(dto);

        if (file != null && !file.isEmpty()) {
            String uploadedUrl = cloudinaryUploader.uploadFile(file);
            if (uploadedUrl != null) {
                deleteFileFromCloudinary(existing.getUrl());
                existing.setUrl(uploadedUrl);
            }
        }

        existing.setIdentifier(dto.getIdentifier());
        existing.setNameCurriculum(dto.getNameCurriculum());
        existing.setLecturer(dto.getLecturer());
        existing.setDescription(dto.getDescription());
        existing.setSubject(dto.getSubject());
        existing.setBranch(dto.getBranch());
        existing.setDepartment(dto.getDepartment());
        existing.setSubjectCode(dto.getSubjectCode());
        existing.setBranchCode(dto.getBranchCode());
        existing.setDepartmentCode(dto.getDepartmentCode());

        return curriculumRepository.save(existing);
    }

    @Transactional
    public void deleteCurriculum(String code) {
        Curriculum curriculum = curriculumRepository.findByCurriculumCode(code);
        if (curriculum == null) {
            throw new EntityNotFoundException("Curriculum with code " + code + " not found");
        }

        if (curriculum.getUrl() != null) {
            deleteFileFromCloudinary(curriculum.getUrl());
        }

        curriculumRepository.deleteById(code);
    }

    public Curriculum getCurriculumByCode(String code) {
        Curriculum curriculum = curriculumRepository.findByCurriculumCode(code);
        if (curriculum == null) {
            throw new EntityNotFoundException("Curriculum with code " + code + " not found");
        }
        return curriculum;
    }

    public List<Curriculum> getAllCurriculums() {
        return curriculumRepository.findAll();
    }

    public List<Curriculum> filterCurriculums(String name, String lecturer, String identifier,
                                              String subject, String branch, String department) {
        Specification<Curriculum> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("nameCurriculum")), "%" + name.toLowerCase() + "%"));
            }
            if (lecturer != null && !lecturer.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("lecturer")), "%" + lecturer.toLowerCase() + "%"));
            }
            if (identifier != null && !identifier.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("identifier")), "%" + identifier.toLowerCase() + "%"));
            }
            if (subject != null && !subject.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("subject")), "%" + subject.toLowerCase() + "%"));
            }
            if (branch != null && !branch.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("branch")), "%" + branch.toLowerCase() + "%"));
            }
            if (department != null && !department.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("department")), "%" + department.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return curriculumRepository.findAll(spec);
    }

    private void validateForeignKeys(CurriculumDTO dto) {
        if (dto.getIdentifier() != null && !userRepository.existsByIdentifier(dto.getIdentifier())) {
            throw new EntityNotFoundException("User with identifier " + dto.getIdentifier() + " not found");
        }
        if (dto.getSubjectCode() != null && !subjectRepository.existsBySubjectCode(dto.getSubjectCode())) {
            throw new EntityNotFoundException("Subject with code " + dto.getSubjectCode() + " not found");
        }
        if (dto.getBranchCode() != null && !branchRepository.existsByBranchCode(dto.getBranchCode())) {
            throw new EntityNotFoundException("Branch with code " + dto.getBranchCode() + " not found");
        }
        if (dto.getDepartmentCode() != null && !schoolRepository.existsByDepartmentCode(dto.getDepartmentCode())) {
            throw new EntityNotFoundException("School with department code " + dto.getDepartmentCode() + " not found");
        }
    }

    private void deleteFileFromCloudinary(String url) {
        if (url == null) return;
        try {
            String publicId = extractPublicIdFromUrl(url);
            cloudinaryUploader.deleteFile(publicId);
        } catch (Exception e) {
            logger.warn("Could not delete file from Cloudinary: {}", e.getMessage());
        }
    }

    private String extractPublicIdFromUrl(String url) {
        String regex = "https://res\\.cloudinary\\.com/[^/]+/raw/upload/v\\d+/(.+?)(\\.[^/.]+)?$";
        Matcher matcher = Pattern.compile(regex).matcher(url);
        if (matcher.find()) return matcher.group(1);
        throw new IllegalArgumentException("Invalid Cloudinary URL: " + url);
    }
}
