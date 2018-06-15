select * from STUDENT left join SCHEDULEDCLASS_STUDENT S ON STUDENT.ID = S.STUDENTS_ID;

select * from COURSE c left join SCHEDULEDCLASS s on c.id = s.COURSE_ID;

insert into scheduledclass_student (students_id, classes_id)
    values (1, 2);

select * from student;

select * from MYORDER