package com.example.nextstep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private static final String DATABASE_NAME = "TaskDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tasks";

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    public String careerPath;
    private static Map<String, List<String>> careerTasks;

    static {
        careerTasks = new HashMap<>();

        // Developer tasks
        List<String> developerTasks = new ArrayList<>();
        developerTasks.add("Learn the basics of a programming language (Start with Python or JavaScript).");
        developerTasks.add("Understand how code works with real examples (print, if-else, loops).");
        developerTasks.add("Try creating simple programs (Calculator, Quiz App, To-Do List).");
        developerTasks.add("Learn how websites and apps are made.");
        developerTasks.add("Explore front-end tools like HTML, CSS, and JavaScript.");
        developerTasks.add("Try building a basic webpage with HTML and CSS.");
        developerTasks.add("Get familiar with Git (Save and track your code).");
        developerTasks.add("Make a GitHub account and upload a project.");
        developerTasks.add("Watch tutorials on mobile/web frameworks (React, Flutter).");
        developerTasks.add("Build a mini project using a framework (like a Weather App).");
        developerTasks.add("Create a simple portfolio website to show your work.");
        developerTasks.add("Join coding communities (Discord, Reddit, Facebook groups).");
        developerTasks.add("Learn the basics of how apps talk to each other (APIs).");
        developerTasks.add("Try a coding challenge website (HackerRank, LeetCode – easy level).");
        developerTasks.add("Look for internships or freelance projects you can join.");
        careerTasks.put(UserResponses.DEVELOPER, developerTasks);

        // Network Specialist tasks
        List<String> networkTasks = new ArrayList<>();
        networkTasks.add("Understand what a network is and how the internet works.");
        networkTasks.add("Learn about IP addresses, routers, and switches.");
        networkTasks.add("Watch videos about how data travels online (OSI Model).");
        networkTasks.add("Try setting up a basic home network with Wi-Fi.");
        networkTasks.add("Learn basic command-line networking tools (ping, ipconfig).");
        networkTasks.add("Use Cisco Packet Tracer to simulate small networks.");
        networkTasks.add("Learn how to keep a network safe (firewalls, strong passwords).");
        networkTasks.add("Explore what VLANs and VPNs are (watch demo videos).");
        networkTasks.add("Practice network setup tasks (basic labs on YouTube).");
        networkTasks.add("Get an intro to cloud networking (AWS, Google Cloud).");
        networkTasks.add("Learn basic subnetting with visuals.");
        networkTasks.add("Join tech forums like Cisco Learning Network.");
        networkTasks.add("Try networking quizzes to test your skills.");
        networkTasks.add("Study beginner CCNA topics (free courses available).");
        networkTasks.add("Look for entry-level IT/networking internships.");
        careerTasks.put(UserResponses.NETWORK_SPECIALIST, networkTasks);

        // IT Support tasks
        List<String> itSupportTasks = new ArrayList<>();
        itSupportTasks.add("Learn about computer parts and how they work together.");
        itSupportTasks.add("Practice solving basic computer problems (slow PC, no internet).");
        itSupportTasks.add("Understand operating systems (Windows, Linux).");
        itSupportTasks.add("Learn how to install apps and drivers.");
        itSupportTasks.add("Practice basic commands in CMD or Terminal.");
        itSupportTasks.add("Try fixing a friend's computer problem (real-life practice).");
        itSupportTasks.add("Learn about user accounts and passwords (admin tools).");
        itSupportTasks.add("Get to know basic networking (IP address, Wi-Fi setup).");
        itSupportTasks.add("Watch videos on system administration tools (Active Directory).");
        itSupportTasks.add("Use free tools like AnyDesk or TeamViewer.");
        itSupportTasks.add("Learn simple scripts (like automating file cleanup).");
        itSupportTasks.add("Practice resetting passwords or setting user access.");
        itSupportTasks.add("Try Linux (Ubuntu) in VirtualBox.");
        itSupportTasks.add("Study for certifications like CompTIA A+ (use free content).");
        itSupportTasks.add("Apply for help desk or tech support internships.");
        careerTasks.put(UserResponses.IT_SUPPORT, itSupportTasks);

        // Data & Analytics tasks
        List<String> dataAnalyticsTasks = new ArrayList<>();
        dataAnalyticsTasks.add("Learn the basics of Microsoft Excel or Google Sheets.");
        dataAnalyticsTasks.add("Practice making charts and graphs from data.");
        dataAnalyticsTasks.add("Study simple statistics (average, median, percent).");
        dataAnalyticsTasks.add("Try SQL (learn how to get data from a database).");
        dataAnalyticsTasks.add("Explore Python basics for data tasks.");
        dataAnalyticsTasks.add("Clean messy data (remove blanks, fix names in Excel).");
        dataAnalyticsTasks.add("Use free datasets to analyze (Kaggle, government open data).");
        dataAnalyticsTasks.add("Create a simple data dashboard (Excel or Google Sheets).");
        dataAnalyticsTasks.add("Try Power BI or Tableau (watch beginner tutorials).");
        dataAnalyticsTasks.add("Understand how data helps in business decisions.");
        dataAnalyticsTasks.add("Learn about data types (numbers, text, dates).");
        dataAnalyticsTasks.add("Do a mini project like analyzing a class survey.");
        dataAnalyticsTasks.add("Join data communities (Kaggle, DataCamp).");
        dataAnalyticsTasks.add("Explore intro to machine learning (watch fun demos).");
        dataAnalyticsTasks.add("Apply for entry-level data analyst opportunities.");
        careerTasks.put(UserResponses.DATA_ANALYTICS, dataAnalyticsTasks);

        // UI Designer tasks
        List<String> uiDesignerTasks = new ArrayList<>();
        uiDesignerTasks.add("Learn what UI (User Interface) and UX (User Experience) mean.");
        uiDesignerTasks.add("Understand the importance of design in apps and websites.");
        uiDesignerTasks.add("Explore Figma (free and easy for beginners).");
        uiDesignerTasks.add("Create simple wireframes for an app (like a food delivery app).");
        uiDesignerTasks.add("Learn about colors, fonts, and layout basics.");
        uiDesignerTasks.add("Watch UI/UX design process videos on YouTube.");
        uiDesignerTasks.add("Practice designing screens (login, profile, home).");
        uiDesignerTasks.add("Try making an interactive prototype in Figma.");
        uiDesignerTasks.add("Learn how to do user research (surveys, interviews).");
        uiDesignerTasks.add("Test designs with friends (usability feedback).");
        uiDesignerTasks.add("Learn the difference between good and bad UX.");
        uiDesignerTasks.add("Join design communities (like UXPin, Dribbble).");
        uiDesignerTasks.add("Build a mini portfolio on Behance or your own website.");
        uiDesignerTasks.add("Learn basic HTML & CSS (helps bring designs to life).");
        uiDesignerTasks.add("Apply for internships in design or freelance UI work.");
        careerTasks.put(UserResponses.UI_DESIGNER, uiDesignerTasks);

        // Project Manager tasks
        List<String> projectManagerTasks = new ArrayList<>();
        projectManagerTasks.add("Understand what a project manager does (watch a short intro video).");
        projectManagerTasks.add("Learn the basics of project management (what is a project, scope, timeline).");
        projectManagerTasks.add("Explore popular methods like Agile, Scrum, and Waterfall (use simple examples).");
        projectManagerTasks.add("Use a task management tool like Trello or Notion to plan a mini project.");
        projectManagerTasks.add("Practice creating a to-do list with deadlines (school project, group work).");
        projectManagerTasks.add("Learn how to break big tasks into smaller ones (called \"task breakdown\").");
        projectManagerTasks.add("Watch how daily standups and sprint planning works in Agile teams.");
        projectManagerTasks.add("Make a simple project timeline or Gantt chart (use Google Sheets or online tools).");
        projectManagerTasks.add("Understand roles in a tech team (designer, developer, tester, PM).");
        projectManagerTasks.add("Improve your communication skills (clear updates, sharing progress).");
        projectManagerTasks.add("Practice writing short reports or project summaries.");
        projectManagerTasks.add("Learn how to spot and reduce risks in a project (delays, missing files, etc.).");
        projectManagerTasks.add("Try leading a small group project in class or as an extracurricular activity.");
        projectManagerTasks.add("Join project management forums or student clubs to practice teamwork.");
        projectManagerTasks.add("Explore beginner certifications like Google Project Management or Scrum Fundamentals.");
        careerTasks.put(UserResponses.PROJECT_MANAGER, projectManagerTasks);

        // Cyber Security tasks
        List<String> cyberSecurityTasks = new ArrayList<>();
        cyberSecurityTasks.add("Understand what Cybersecurity is (watch an intro video on how hacking works).");
        cyberSecurityTasks.add("Learn about digital threats (malware, phishing, scams).");
        cyberSecurityTasks.add("Understand the CIA Triad (Confidentiality, Integrity, Availability).");
        cyberSecurityTasks.add("Secure your own devices (use strong passwords, enable 2FA).");
        cyberSecurityTasks.add("Learn basic networking (IP address, ports, firewalls).");
        cyberSecurityTasks.add("Try using a virtual machine (install Kali Linux or Ubuntu in VirtualBox).");
        cyberSecurityTasks.add("Practice basic Linux commands (cd, ls, mkdir, etc.).");
        cyberSecurityTasks.add("Explore ethical hacking tools (like Nmap and Wireshark – watch demos first).");
        cyberSecurityTasks.add("Understand encryption & how data is protected (SSL, HTTPS, basic cryptography).");
        cyberSecurityTasks.add("Learn about firewalls, antivirus, and VPNs (what they do and why they matter).");
        cyberSecurityTasks.add("Try basic Capture The Flag (CTF) games (from TryHackMe or Hack The Box – Beginner level).");
        cyberSecurityTasks.add("Explore a day in the life of a cybersecurity analyst (watch real stories).");
        cyberSecurityTasks.add("Learn how to recognize social engineering attacks (phishing, baiting).");
        cyberSecurityTasks.add("Get familiar with cybersecurity careers (SOC analyst, penetration tester, etc.).");
        cyberSecurityTasks.add("Start studying for beginner certs (like CompTIA Security+ or Google Cybersecurity Certificate).");
        careerTasks.put(UserResponses.CYBER_SECURITY, cyberSecurityTasks);
    }


    public TaskManager(Context context, String careerPath) {
        this.careerPath = careerPath;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        initializeTasks();
    }

    private void initializeTasks() {
        List<String> descriptions = careerTasks.get(careerPath);
        if (descriptions != null) {
            for (int i = 0; i < descriptions.size(); i++) {
                int id = getTaskId(i);
                if (!taskExists(id)) {
                    db.execSQL("INSERT INTO " + TABLE_NAME + " (id, career, description, completed) VALUES (?, ?, ?, 0)",
                            new Object[]{id, careerPath, descriptions.get(i)});
                }
            }
        }
    }

    private boolean taskExists(int taskId) {
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(taskId)});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public List<Task> getTasksForCareer() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT id, description, completed FROM " + TABLE_NAME + " WHERE career = ?", new String[]{careerPath});

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String description = cursor.getString(1);
            boolean completed = cursor.getInt(2) == 1;
            tasks.add(new Task(id, description, completed));
        }

        cursor.close();
        return tasks;
    }

    private int getTaskId(int position) {
        return careerPath.hashCode() + position;
    }

    public void setTaskCompleted(int taskId, boolean completed) {
        db.execSQL("UPDATE " + TABLE_NAME + " SET completed = ? WHERE id = ?",
                new Object[]{completed ? 1 : 0, taskId});
    }

    public boolean isTaskCompleted(int taskId) {
        Cursor cursor = db.rawQuery("SELECT completed FROM " + TABLE_NAME + " WHERE id = ?",
                new String[]{String.valueOf(taskId)});
        boolean result = false;
        if (cursor.moveToFirst()) {
            result = cursor.getInt(0) == 1;
        }
        cursor.close();
        return result;
    }

    public int getCompletedTaskCount() {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE career = ? AND completed = 1", new String[]{careerPath});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getTotalTaskCount() {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE career = ?", new String[]{careerPath});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public void resetAllTasks() {
        db.execSQL("UPDATE " + TABLE_NAME + " SET completed = 0 WHERE career = ?", new Object[]{careerPath});
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY," +
                    "career TEXT," +
                    "description TEXT," +
                    "completed INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
