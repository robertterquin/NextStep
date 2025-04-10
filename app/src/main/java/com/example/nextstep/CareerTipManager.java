package com.example.nextstep;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CareerTipManager {
    
    private Context context;
    private Map<String, String[]> careerTips;
    private Random random;
    
    public CareerTipManager(Context context) {
        this.context = context;
        this.random = new Random();
        initializeCareerTips();
    }
    
    private void initializeCareerTips() {
        careerTips = new HashMap<>();
        
        // Developer tips
        careerTips.put(UserResponses.DEVELOPER, new String[] {
            "Learn version control systems like Git to better manage your code.",
            "Practice coding challenges on platforms like LeetCode or HackerRank.",
            "Build a portfolio of personal projects to showcase your skills.",
            "Stay updated with new programming languages and frameworks.",
            "Contribute to open-source projects to gain real-world experience."
        });
        
        // Network Specialist tips
        careerTips.put(UserResponses.NETWORK_SPECIALIST, new String[] {
            "Get certified in network technologies like CCNA or CompTIA Network+.",
            "Practice network configurations in virtual environments.",
            "Learn about cloud networking services from AWS, Azure, or GCP.",
            "Study network security principles and best practices.",
            "Keep up with emerging technologies like SD-WAN and 5G."
        });
        
        // IT Support tips
        careerTips.put(UserResponses.IT_SUPPORT, new String[] {
            "Develop strong communication skills to explain technical issues clearly.",
            "Practice troubleshooting in various environments and scenarios.",
            "Learn about common operating systems and their administration.",
            "Consider getting CompTIA A+ certification for career advancement.",
            "Practice customer service skills alongside technical knowledge."
        });
        
        // Data Analytics tips
        careerTips.put(UserResponses.DATA_ANALYTICS, new String[] {
            "Learn SQL for effective data manipulation and querying.",
            "Master data visualization tools like Tableau or Power BI.",
            "Study statistics fundamentals to better interpret data.",
            "Learn programming languages like Python or R for data analysis.",
            "Practice cleaning and preparing messy datasets for analysis."
        });
        
        // UI Designer tips
        careerTips.put(UserResponses.UI_DESIGNER, new String[] {
            "Build a strong portfolio showcasing your UI/UX design skills.",
            "Stay updated with the latest design trends and principles.",
            "Learn to use industry-standard tools like Figma or Adobe XD.",
            "Study user psychology to create more intuitive interfaces.",
            "Practice presenting and explaining your design decisions."
        });
        
        // Project Manager tips
        careerTips.put(UserResponses.PROJECT_MANAGER, new String[] {
            "Learn different project management methodologies like Agile and Waterfall.",
            "Develop strong communication and leadership skills.",
            "Practice creating project plans, timelines, and budgets.",
            "Consider certifications like PMP or PRINCE2.",
            "Improve your stakeholder management and conflict resolution skills."
        });
        
        // Cyber Security tips
        careerTips.put(UserResponses.CYBER_SECURITY, new String[] {
            "Practice ethical hacking in controlled environments like CTF competitions.",
            "Learn about common vulnerabilities and protection mechanisms.",
            "Stay updated with the latest security threats and solutions.",
            "Consider certifications like CompTIA Security+ or CEH.",
            "Understand security frameworks like NIST or ISO 27001."
        });
    }
    
    public String getRandomTipForCareer(String career) {
        if (careerTips.containsKey(career)) {
            String[] tips = careerTips.get(career);
            return tips[random.nextInt(tips.length)];
        } else {
            return "Set clear goals and work consistently towards achieving them.";
        }
    }
    
    public String[] getAllTipsForCareer(String career) {
        if (careerTips.containsKey(career)) {
            return careerTips.get(career);
        } else {
            return new String[]{"No specific tips available for this career path yet."};
        }
    }
}
