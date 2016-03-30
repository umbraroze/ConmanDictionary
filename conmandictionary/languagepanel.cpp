#include "languagepanel.h"
#include "ui_languagepanel.h"

LanguagePanel::LanguagePanel(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::LanguagePanel)
{
    ui->setupUi(this);
}

LanguagePanel::~LanguagePanel()
{
    delete ui;
}
