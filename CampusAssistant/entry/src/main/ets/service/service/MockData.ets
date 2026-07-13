import { Constants } from '../common/Constants';
import { CourseSchedule } from '../model/Models';

export class MockData {
  static getScheduleList(): CourseSchedule[] {
    const list: CourseSchedule[] = [];
    const s1: CourseSchedule = new CourseSchedule();
    s1.id = 1; s1.courseName = '高等数学'; s1.classroom = '致远楼301'; s1.dayOfWeek = 1;
    s1.startSection = 1; s1.endSection = 2; s1.teacherName = '李教授'; s1.colorIndex = 0;
    list.push(s1);

    const s2: CourseSchedule = new CourseSchedule();
    s2.id = 2; s2.courseName = '大学英语'; s2.classroom = '明德楼205'; s2.dayOfWeek = 1;
    s2.startSection = 3; s2.endSection = 4; s2.teacherName = '王老师'; s2.colorIndex = 1;
    list.push(s2);

    const s3: CourseSchedule = new CourseSchedule();
    s3.id = 3; s3.courseName = '数据结构'; s3.classroom = '致远楼402'; s3.dayOfWeek = 2;
    s3.startSection = 1; s3.endSection = 2; s3.teacherName = '赵教授'; s3.colorIndex = 2;
    list.push(s3);

    const s4: CourseSchedule = new CourseSchedule();
    s4.id = 4; s4.courseName = '计算机网络'; s4.classroom = '博学楼108'; s4.dayOfWeek = 2;
    s4.startSection = 5; s4.endSection = 6; s4.teacherName = '孙老师'; s4.colorIndex = 3;
    list.push(s4);

    const s5: CourseSchedule = new CourseSchedule();
    s5.id = 5; s5.courseName = '操作系统'; s5.classroom = '致远楼301'; s5.dayOfWeek = 3;
    s5.startSection = 3; s5.endSection = 4; s5.teacherName = '周教授'; s5.colorIndex = 4;
    list.push(s5);

    const s6: CourseSchedule = new CourseSchedule();
    s6.id = 6; s6.courseName = '线性代数'; s6.classroom = '明德楼301'; s6.dayOfWeek = 4;
    s6.startSection = 1; s6.endSection = 2; s6.teacherName = '吴老师'; s6.colorIndex = 5;
    list.push(s6);

    const s7: CourseSchedule = new CourseSchedule();
    s7.id = 7; s7.courseName = '物联网导论'; s7.classroom = '博学楼205'; s7.dayOfWeek = 5;
    s7.startSection = 1; s7.endSection = 2; s7.teacherName = '郑教授'; s7.colorIndex = 6;
    list.push(s7);

    return list;
  }
}

export interface AnnouncementItem {
  title: string;
  category: string;
  time: string;
  summary: string;
}
