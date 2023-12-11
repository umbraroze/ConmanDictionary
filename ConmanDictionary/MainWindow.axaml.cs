using Avalonia;
using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using ReactiveUI;
using System.Reactive;
using System.ComponentModel;
using System.Diagnostics;

namespace ConmanDictionary
{

    public class MainWindowViewModel : INotifyPropertyChanged
    {
        string buttonText = "Click Me!";

        private MainWindow? _mainWindow;

        public MainWindowViewModel(MainWindow parent) : base()
        {
            _mainWindow = parent;
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

        public event PropertyChangedEventHandler? PropertyChanged;

        public void ButtonClicked() => ButtonText = "Hello, Avalonia!";

        public void QuitCommand()
        {
            System.Diagnostics.Debug.WriteLine("Quit");
            _mainWindow?.Close();
        }

        public async void ShowAboutWindowCommand()
        {
            if (_mainWindow == null)
                return;
            // System.Diagnostics.Debug.WriteLine("Show About Window");
            AboutWindow about = new AboutWindow();
            await about.ShowDialog(_mainWindow);
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
