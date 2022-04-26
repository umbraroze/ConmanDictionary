using Avalonia;
using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using ReactiveUI;
using System.Reactive;
using System.ComponentModel;

namespace ConmanDictionary
{

    public class MainWindowViewModel : INotifyPropertyChanged
    {
        string buttonText = "Click Me!";

        private Window parent;
        private AboutWindow aboutWindow;

        public MainWindowViewModel(Window parent) : base()
        {
            this.parent = parent;
        }

        public string ButtonText
        {
            get => buttonText;
            set
            {
                buttonText = value;
                PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(nameof(ButtonText)));
            }
        }

        public event PropertyChangedEventHandler PropertyChanged;

        public void ButtonClicked() => ButtonText = "Hello, Avalonia!";

        public void QuitCommand()
        {
            System.Diagnostics.Debug.WriteLine("Quit");
            parent.Close();
        }

        public void ShowAboutWindowCommand()
        {
            System.Diagnostics.Debug.WriteLine("Show About Window");
            if(aboutWindow == null)
            {
                aboutWindow = new AboutWindow();
                aboutWindow.Show();
            }
        }
    }

    public partial class MainWindow : Window
    {

        public MainWindow()
        {
            InitializeComponent();
            DataContext = new MainWindowViewModel(this);
#if DEBUG
            this.AttachDevTools();
#endif
        }


        private void InitializeComponent()
        {
            AvaloniaXamlLoader.Load(this);
        }

    }
}
