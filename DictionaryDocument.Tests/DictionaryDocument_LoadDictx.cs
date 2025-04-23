namespace DictionaryDocument.Tests;

[TestFixture]
public class LoadDictx
{
    private static readonly string[] _TestFiles = {
            @"TestFiles/simplefile.xml",
            @"TestFiles/complexfile.xml",
            @"TestFiles/complexfile2.xml",
            @"TestFiles/complexfiles_mergedbyhand.xml",
            @"TestFiles/mock_document.xml"
    };
    private static readonly string generatedMockDocumentFile = @"TestFiles/mock_document.xml";

    [SetUp]
    public void Setup()
    {
        
    }

    [Test]
    public void TestFilesWereDeployed()
    {
        foreach (String file in _TestFiles)
        {
            Assert.That(File.Exists(file),$"Testing environment build failed: {file} not found.");
        }
    }

    [Test]
    public void TestFilesAreValid()
    {
        foreach (String file in _TestFiles)
        {
            var f = new FileInfo(file);
            Assert.That(DictionaryDocument.Dictionary.ValidateDictx(f), $"Validation of a test data file {file} failed.");
        }
    }

    [Test]
    public void CompareGeneratedToStaticFile()
    {
        // Generate mock document.
        Dictionary mockDocument1 = Generators.GetMockDocument();
        // Load the test file.
        Dictionary mockDocument2 = Dictionary.LoadDictx(new FileInfo(generatedMockDocumentFile));
        Assert.That(mockDocument1, Is.EqualTo(mockDocument2), $"The generated document doesn't match the loaded document.");
    }

}