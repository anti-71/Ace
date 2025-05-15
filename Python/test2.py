import matplotlib.pyplot as plt
import matplotlib.dates as mdates
from datetime import datetime, timedelta
import numpy as np
from matplotlib import rcParams

# 1. 设置中文字体（解决中文显示问题）
rcParams['font.sans-serif'] = ['SimHei']  # Windows系统
# rcParams['font.sans-serif'] = ['Arial Unicode MS']  # Mac系统
rcParams['axes.unicode_minus'] = False  # 解决负号显示问题

# 2. 设置任务数据（从2025-05-01开始）
base_date = datetime(2025, 5, 1)
tasks = [
    {"name": "1. 选题与导师确认", "duration": 2},
    {"name": "2. 文献查阅与综述", "duration": 3},
    {"name": "3. 开题报告撰写", "duration": 2},
    {"name": "4. 实验/数据分析", "duration": 6},
    {"name": "5. 初稿撰写", "duration": 3},
    {"name": "6. 修改与润色", "duration": 2},
    {"name": "7. 答辩准备", "duration": 1},
]

# 3. 自动计算日期和进度
start_dates = []
end_dates = []
current_date = base_date
today = datetime.now().date()  # 获取当前日期

for task in tasks:
    start_dates.append(current_date)
    end_date = current_date + timedelta(weeks=task["duration"])
    end_dates.append(end_date)
    
    # 计算进度
    if today >= end_date.date():
        task["progress"] = 100
    elif today < current_date.date():
        task["progress"] = 0
    else:
        elapsed_days = (today - current_date.date()).days + 1  # 包含当天
        total_days = (end_date.date() - current_date.date()).days
        task["progress"] = min(100, int((elapsed_days / total_days) * 100))
    
    current_date = end_date

# 4. 创建甘特图
fig, ax = plt.subplots(figsize=(12, 6))

# 颜色设置
colors = plt.cm.tab20(np.linspace(0, 1, len(tasks)))

# 绘制任务条
for i, (task, start, end) in enumerate(zip(tasks, start_dates, end_dates)):
    duration = (end - start).days
    completed_days = duration * task["progress"] / 100
    
    # 已完成部分
    ax.barh(task["name"], 
            completed_days, 
            left=start, 
            color=colors[i], 
            edgecolor='black',
            label=f'已完成 {task["progress"]}%')
    
    # 未完成部分
    if task["progress"] < 100:
        ax.barh(task["name"], 
                duration - completed_days, 
                left=start + timedelta(days=completed_days), 
                color=colors[i], 
                alpha=0.3,
                edgecolor='black')

# 5. 美化图表
ax.set_title("毕业设计进度甘特图（2025年5月1日-8月21日）", pad=20, fontsize=14)
ax.set_xlabel("时间轴", fontsize=10)
ax.xaxis.set_major_locator(mdates.WeekdayLocator(byweekday=mdates.MO, interval=1))
ax.xaxis.set_major_formatter(mdates.DateFormatter("%m-%d"))
plt.xticks(rotation=45)

# 添加今日标记线
ax.axvline(x=today, color='red', linestyle='--', label=f'当前日期 ({today.strftime("%Y-%m-%d")})')
ax.legend(loc='lower right')

plt.grid(axis='x', linestyle='--', alpha=0.5)
plt.tight_layout()
          
# 6. 保存和显示
plt.savefig('毕业设计进度.png', dpi=300, bbox_inches='tight')
plt.show()