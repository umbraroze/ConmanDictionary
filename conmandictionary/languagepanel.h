#ifndef LANGUAGEPANEL_H
#define LANGUAGEPANEL_H

#include <QWidget>

namespace Ui {
class LanguagePanel;
}

class LanguagePanel : public QWidget
{
    Q_OBJECT

public:
    explicit LanguagePanel(QWidget *parent = 0);
    ~LanguagePanel();

private:
    Ui::LanguagePanel *ui;
};

#endif // LANGUAGEPANEL_H
