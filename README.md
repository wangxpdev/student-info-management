# Student Information Management System
[![Tech Stack](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Tech Stack](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/HTML)
[![Tech Stack](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS)
## 📋 项目简介 (Overview)

这是一个完整的学生信息管理系统，旨在简化校园数据的 CRUD（创建、读取、更新、删除）操作。本项目使用了现代化的两层架构，展示了全栈开发的基本能力。

**关键特性:**
* **数据持久化**：使用 `Students.json` 文件进行数据存储，保证数据不会在程序关闭后丢失。
* **前后端分离**：前端使用标准的 HTML/CSS 实现界面，后端使用 Java 处理业务逻辑。
* **命令运行**：提供 Windows 批处理文件 (`.bat`) 实现快速启动和运行。
* ## ⚙️ 技术栈 (Tech Stack)

**后端**
* 语言: Java (JDK 8/17+)
* 依赖: 无外部库，纯净的 Java I/O 和核心类。

**前端**
* 标记语言: HTML5
* 样式: CSS3

**数据存储**
* 格式: JSON (通过 `FileUtil.java` 管理)

* ## ▶️ 本地运行指南 (How to Run Locally)

**前提条件:**
1.  已安装 Java 运行时环境 (JRE)。

**步骤:**
1.  克隆仓库:
    `git clone https://github.com/wangxpdev/student-info-management.git`
2.  进入项目目录:
    `cd student-info-management`
3.  运行后端服务:
    双击运行或在命令行执行 `run_backend.bat`。
4.  打开前端界面:
    在浏览器中打开 `public/index.html` 文件 即可访问管理系统。
