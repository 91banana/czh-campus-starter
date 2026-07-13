export class Constants {
  static readonly APP_NAME: string = '智能校园助手';
  static readonly GRADE_OPTIONS: string[] = ['2023', '2024', '2025', '2026'];
  static readonly COLLEGE_OPTIONS: string[] = ['计算机学院', '航空工程学院', '电子信息学院', '航空安全学院', '空中乘务学院', '管理学院', '外语学院'];
  static readonly MAJOR_OPTIONS_MAP: Record<string, string[]> = {
    '计算机学院': ['计算机科学与技术', '软件工程', '物联网工程', '数据科学与大数据技术'],
    '航空工程学院': ['飞行器动力工程', '飞行器制造工程', '航空航天工程'],
    '电子信息学院': ['电子信息工程', '通信工程', '电气工程及其自动化'],
    '航空安全学院': ['安全工程', '消防工程'],
    '空中乘务学院': ['空中乘务', '民航空中安全保卫'],
    '管理学院': ['工商管理', '公共事业管理', '物流管理'],
    '外语学院': ['英语', '翻译']
  };
  static readonly CLASS_PREFIX_MAP: Record<string, string> = {
    '物联网工程': '物联网',
    '计算机科学与技术': '计科',
    '软件工程': '软工',
    '数据科学与大数据技术': '大数据',
    '飞行器动力工程': '飞动',
    '飞行器制造工程': '飞制',
    '航空航天工程': '空天',
    '电子信息工程': '电信',
    '通信工程': '通信',
    '电气工程及其自动化': '电气',
    '安全工程': '安全',
    '消防工程': '消防',
    '空中乘务': '乘务',
    '民航空中安全保卫': '空保',
    '工商管理': '工商',
    '公共事业管理': '公管',
    '物流管理': '物流',
    '英语': '英语',
    '翻译': '翻译'
  };
  static readonly CLASS_COUNT_MAP: Record<string, number> = {
    '物联网工程': 2,
    '计算机科学与技术': 2,
    '软件工程': 2,
    '数据科学与大数据技术': 1,
    '飞行器动力工程': 2,
    '飞行器制造工程': 2,
    '航空航天工程': 1,
    '电子信息工程': 2,
    '通信工程': 2,
    '电气工程及其自动化': 1,
    '安全工程': 1,
    '消防工程': 1,
    '空中乘务': 2,
    '民航空中安全保卫': 1,
    '工商管理': 2,
    '公共事业管理': 1,
    '物流管理': 1,
    '英语': 2,
    '翻译': 1
  };
  static getClassOptions(major: string, grade: string): string[] {
    const prefix: string = Constants.CLASS_PREFIX_MAP[major] ?? '';
    const count: number = Constants.CLASS_COUNT_MAP[major] ?? 2;
    const shortYear: string = grade.length >= 4 ? grade.substring(2) : grade;
    const list: string[] = [];
    for (let i = 1; i <= count; i++) {
      list.push(`${prefix}${shortYear}${i < 10 ? '0' : ''}${i}`);
    }
    return list;
  }
  static readonly SCHEDULE_COLORS: string[] = [
    '#4A90D9', '#50C878', '#FF6B6B', '#FFB347', '#9B59B6',
    '#1ABC9C', '#E74C3C', '#3498DB', '#F39C12', '#2ECC71'
  ];
  static readonly SECTION_TIMES: string[][] = [
    ['08:30', '09:15'],
    ['09:20', '10:05'],
    ['10:25', '11:10'],
    ['11:15', '12:00'],
    ['14:30', '15:15'],
    ['15:20', '16:05'],
    ['16:25', '17:10'],
    ['17:15', '18:00'],
    ['19:30', '20:15'],
    ['20:20', '21:05']
  ];
  static readonly PRODUCT_CATEGORIES: string[] = ['全部', '教材书籍', '电子数码', '生活用品', '运动器材', '服饰鞋包', '其他'];
  static readonly CATEGORY_NAME_TO_ID: Record<string, number> = {
    '教材书籍': 1,
    '电子数码': 2,
    '生活用品': 3,
    '运动器材': 4,
    '服饰鞋包': 5,
    '其他': 6
  } as Record<string, number>;
  static readonly CAMPUS_LOCATIONS: string[] = ['1教', '2教', '3教', '4教', '5教', '食堂', '图书馆', '宿舍区'];
  static readonly CONDITION_OPTIONS: string[] = ['全新', '几乎全新', '轻微使用痕迹', '明显使用痕迹', '成色一般'];
  static readonly ANNOUNCEMENT_CATEGORIES: string[] = ['全部', '教务', '学工', '社团', '后勤', '紧急'];
  static readonly HOMETOWN_OPTIONS: string[] = [
    '北京', '天津', '上海', '重庆',
    '河北', '山西', '辽宁', '吉林', '黑龙江',
    '江苏', '浙江', '安徽', '福建', '江西', '山东',
    '河南', '湖北', '湖南', '广东', '海南',
    '四川', '贵州', '云南', '陕西',
    '甘肃', '青海', '台湾',
    '内蒙古', '广西', '西藏', '宁夏', '新疆'
  ];
}
