/*
 * 
 * This application exists mostly to try out various concepts during the development
 * without the overhead of the main application. In other words, I don't know a thing
 * about C#, I'm just fooling around here, this is not the real app, please go away.
 *
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DictionaryDocument;

namespace TryConcept
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Out.WriteLine("Start of the app");

            DictionaryDocument.Dictionary d = new DictionaryDocument.Dictionary();

            Console.Out.WriteLine(d);

            Console.Out.WriteLine("End of the app");
        }
    }
}
