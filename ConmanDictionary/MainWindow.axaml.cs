using Avalonia;
using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using ReactiveUI;
using System.Reactive;

namespace ConmanDictionary {

    public class MainWindowViewModel {
        public ReactiveCommand<Unit, Unit> QuitCommand { get; }

        public MainWindowViewModel()
        {
            QuitCommand = ReactiveCommand.Create(Quit);

        }
        void Quit()
        {
            System.Diagnostics.Debug.WriteLine("Quit");
        }
    }

    public partial class MainWindow : Window
    {

        public MainWindow()
        {
            InitializeComponent();
            DataContext = new MainWindowViewModel();
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
