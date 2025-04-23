namespace DictionaryDocument.Tests;

[TestFixture]
public class LoadDictx
{
    private static readonly string[] _TestFiles = {
            @"TestFiles/simplefile.xml",
            @"TestFiles/complexfile.xml",
            @"TestFiles/complexfile2.xml",
            @"TestFiles/complexfiles_mergedbyhand.xml"
    };

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

}