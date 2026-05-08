package test;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DiaryUI extends JFrame {
    private final DiaryDAO dao = new DiaryDAO();

    private JTextField titleField;
    private JTextArea contentArea;
    private JTextArea displayArea;
    private JTextField searchField;

    public DiaryUI() {
        setTitle("My Beautiful Diary");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 색상 테마 (부드러운 파스텔)
        Color bgColor = new Color(245, 240, 235);
        Color accentColor = new Color(100, 150, 180);

        getContentPane().setBackground(bgColor);

        // ==================== 레이아웃 ====================
        setLayout(new BorderLayout(10, 10));

        // 상단: 입력 패널
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBackground(bgColor);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        titleField = new JTextField(30);
        titleField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        contentArea = new JTextArea(8, 50);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));

        inputPanel.add(new JLabel("제목:"), BorderLayout.NORTH);
        inputPanel.add(titleField, BorderLayout.CENTER);
        inputPanel.add(new JScrollPane(contentArea), BorderLayout.SOUTH);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(bgColor);

        JButton saveBtn = createStyledButton("저장", accentColor);
        JButton loadBtn = createStyledButton("전체 불러오기", new Color(80, 160, 120));
        JButton deleteBtn = createStyledButton("선택 삭제", new Color(200, 80, 80));
        JButton searchBtn = createStyledButton("검색", accentColor);

        buttonPanel.add(saveBtn);
        buttonPanel.add(loadBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(searchBtn);

        // 검색 필드
        searchField = new JTextField(20);
        buttonPanel.add(new JLabel("검색:"));
        buttonPanel.add(searchField);

        // 중앙: 표시 영역
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        displayArea.setBackground(new Color(255, 252, 242));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("일기 목록"));

        // 이미지 패널 (하단)
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/diary_visual.png"));
            Image img = icon.getImage().getScaledInstance(400, 180, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel.setText("(이미지 자리 - diary_visual.png)");
            imageLabel.setFont(new Font("맑은 고딕", Font.ITALIC, 14));
        }

        // 조립
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(bgColor);
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(imageLabel, BorderLayout.SOUTH);

        // 이벤트 연결
        saveBtn.addActionListener(e -> saveEntry());
        loadBtn.addActionListener(e -> loadEntries());
        deleteBtn.addActionListener(e -> deleteSelectedEntry());
        searchBtn.addActionListener(e -> searchEntries());
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        return btn;
    }

    private void saveEntry() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제목과 내용을 모두 입력해주세요.");
            return;
        }

        DiaryEntry entry = new DiaryEntry(title, content);
        dao.saveEntry(entry);

        JOptionPane.showMessageDialog(this, "일기가 저장되었습니다.");
        titleField.setText("");
        contentArea.setText("");
        loadEntries();
    }

    private void loadEntries() {
        List<DiaryEntry> entries = dao.loadEntries();
        StringBuilder sb = new StringBuilder();
        for (DiaryEntry e : entries) {
            sb.append(e.toString()).append("\n");
            sb.append(e.getContent()).append("\n");
            sb.append("=".repeat(60)).append("\n\n");
        }
        displayArea.setText(sb.toString());
    }

    private void deleteSelectedEntry() {
        String input = JOptionPane.showInputDialog(this, "삭제할 일기 ID를 입력하세요:");
        if (input == null || input.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(input.trim());
            dao.deleteEntry(id);
            JOptionPane.showMessageDialog(this, "삭제되었습니다.");
            loadEntries();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "올바른 숫자를 입력해주세요.");
        }
    }

    private void searchEntries() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadEntries();
            return;
        }

        List<DiaryEntry> entries = dao.searchEntries(keyword);
        StringBuilder sb = new StringBuilder("검색 결과: '" + keyword + "'\n\n");
        for (DiaryEntry e : entries) {
            sb.append(e.toString()).append("\n");
            sb.append(e.getContent()).append("\n");
            sb.append("=".repeat(50)).append("\n\n");
        }
        displayArea.setText(sb.toString());
    }
}