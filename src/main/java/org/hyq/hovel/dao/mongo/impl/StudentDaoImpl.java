package org.hyq.hovel.dao.mongo.impl;

import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import org.hyq.hovel.dao.mongo.StudentDao;
import org.hyq.hovel.entity.mongo.StudentInfo;
import org.hyq.hovel.model.vo.StudentVo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Repository
public class StudentDaoImpl implements StudentDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public List<StudentInfo> findStudent() {
        Criteria criteria = new Criteria();
        Query query = new Query().addCriteria(criteria.orOperator(Criteria.where("name").is("Jack"), Criteria.where("classId").exists(false))); //
        List<StudentInfo> studentInfos = mongoTemplate.find(query,StudentInfo.class); // ,"student_info"
        return studentInfos;
    }

    @Override
    public List<StudentVo> findStudentVo() {
        Criteria criteria = new Criteria();
        LookupOperation lookup = LookupOperation.newLookup().from("class_info").localField("classId").foreignField("_id").as("classInfo");
        Aggregation aggregation = Aggregation.newAggregation(
                lookup
                ,Aggregation.match(criteria.orOperator(Criteria.where("name").is("Tom").and("classId").is(202304l), Criteria.where("classId").exists(false)))
                ,Aggregation.unwind("classInfo",true)
                ,Aggregation.group("classInfo._id")
                        .first("name").as("studentName")
                        .first("classInfo.studentCount").as("studentCount")
                        .first("classInfo.name").as("className")
                        .first("classId").as("classId")
//                ,Aggregation.project("studentName","classId","studentCount")
        );

        List<StudentVo> sList = mongoTemplate.aggregate(aggregation, "student_info" ,StudentVo.class).getMappedResults();
        return sList;
    }

    @Override
    public boolean saveStudent(StudentInfo studentInfo) {
        try{
            mongoTemplate.insert(studentInfo);
            return true;
        }catch (Exception e){
            log.error("学生：{}信息已存在！请勿重复录入",studentInfo,e);
            return false;
        }
    }
}
