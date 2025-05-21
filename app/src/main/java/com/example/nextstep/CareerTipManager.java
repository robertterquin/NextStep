package com.example.nextstep;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CareerTipManager {

    private Context context;
    private Map<String, String[]> careerTips;
    private Map<String, String[]> careerQuotes;
    private Random random;
    private SharedPreferences prefs;
    private static final String PREF_NAME = "career_quotes_prefs";
    private static final String LAST_QUOTE_KEY = "last_quote_index_";

    public CareerTipManager(Context context) {
        this.context = context;
        this.random = new Random();
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        initializeCareerTips();
        initializeCareerQuotes();
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

    private void initializeCareerQuotes() {
        careerQuotes = new HashMap<>();

        // Developer quotes
        careerQuotes.put(UserResponses.DEVELOPER, new String[] {
                "💻 \"Programs must be written for people to read, and only incidentally for machines to execute.\" – Harold Abelson",
                "💻 \"The best way to get a project done faster is to start sooner.\" – Jim Highsmith",
                "💻 \"Code is like humor. When you have to explain it, it's bad.\" – Cory House"
        });

        // Network Specialist quotes
        careerQuotes.put(UserResponses.NETWORK_SPECIALIST, new String[] {
                "🌐 \"The network is the computer.\" – John Gage",
                "🌐 \"To be a great network engineer, think like a hacker, act like a defender.\"",
                "🌐 \"Behind every seamless connection is a silent network warrior.\""
        });

        // IT Support quotes
        careerQuotes.put(UserResponses.IT_SUPPORT, new String[] {
                "🖥 \"The best support comes from those who understand both people and machines.\"",
                "🖥 \"Sometimes rebooting is all you need — in tech and in life.\"",
                "🖥 \"Helping one user might feel small, but you're the reason their whole system runs.\""
        });

        // Data Analytics quotes
        careerQuotes.put(UserResponses.DATA_ANALYTICS, new String[] {
                "📊 \"Without data, you're just another person with an opinion.\" – W. Edwards Deming",
                "📊 \"Data is the new oil, but it's only valuable when refined.\"",
                "📊 \"The goal is to turn data into information, and information into insight.\" – Carly Fiorina"
        });

        // UI Designer quotes
        careerQuotes.put(UserResponses.UI_DESIGNER, new String[] {
                "🎨 \"Design is not just what it looks like and feels like. Design is how it works.\" – Steve Jobs",
                "🎨 \"Good design is obvious. Great design is transparent.\" – Joe Sparano",
                "🎨 \"Design creates culture. Culture shapes values. Values determine the future.\" – Robert L. Peters"
        });

        // Project Manager quotes
        careerQuotes.put(UserResponses.PROJECT_MANAGER, new String[] {
                "📅 \"Plans are nothing; planning is everything.\" – Dwight D. Eisenhower",
                "📅 \"A goal without a plan is just a wish.\" – Antoine de Saint-Exupéry",
                "📅 \"You don't manage people—you lead them.\""
        });

        // Cyber Security quotes
        careerQuotes.put(UserResponses.CYBER_SECURITY, new String[] {
                "🔐 \"Security is not a product, but a process.\" – Bruce Schneier",
                "🔐 \"Cybersecurity is much more than a matter of IT.\" – Stephane Nappo",
                "🔐 \"You can't defend what you don't understand.\""
        });
    }

    public String getRandomTipForCareer(String career) {
        // Check if we should show a quote or a tip
        if (Math.random() < 0.5) {
            return getNextQuoteForCareer(career);
        } else {
            if (careerTips.containsKey(career)) {
                String[] tips = careerTips.get(career);
                return tips[random.nextInt(tips.length)];
            } else {
                return "Set clear goals and work consistently towards achieving them.";
            }
        }
    }

    public String getNextQuoteForCareer(String career) {
        if (!careerQuotes.containsKey(career)) {
            return "Believe in your ability to succeed.";
        }

        String[] quotes = careerQuotes.get(career);
        int quoteCount = quotes.length;

        // Get the last index we showed for this career
        int lastIndex = prefs.getInt(LAST_QUOTE_KEY + career, -1);

        // Get the next index, ensuring we don't repeat until we've shown all quotes
        int nextIndex = (lastIndex + 1) % quoteCount;

        // Save this index as the last one shown
        prefs.edit().putInt(LAST_QUOTE_KEY + career, nextIndex).apply();

        // Return the quote at the next index
        return quotes[nextIndex];
    }

    public String[] getAllTipsForCareer(String career) {
        if (careerTips.containsKey(career)) {
            return careerTips.get(career);
        } else {
            return new String[]{"No specific tips available for this career path yet."};
        }
    }

    public String[] getAllQuotesForCareer(String career) {
        if (careerQuotes.containsKey(career)) {
            return careerQuotes.get(career);
        } else {
            return new String[]{"No specific quotes available for this career path yet."};
        }
    }
}
