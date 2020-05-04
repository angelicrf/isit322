using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Linq;
using WithVisualStudio.Controllers;
using WithVisualStudio.Models;
using Xunit;

namespace WithVisualStudio.Tests
{
    public class HomeControllerTests
    {
        
        class ModelCompleteFakeRepository : IRepository
        {
            public IEnumerable<Product> Products { get; } = new Product[] {
                new Product { Name = "P1", Price = 275M },
                new Product { Name = "P2", Price = 48.95M },
                new Product { Name = "P3", Price = 19.50M },
                new Product { Name = "P3", Price = 34.95M }};

            public void AddProduct(Product p)
            {
                // Not done yet
            }
        }
        
        [Fact]
        public void IndexActionModelIsComplete()
        {
         
            ModelCompleteFakeRepository  rf = new ModelCompleteFakeRepository();
            var tfg = rf.Products.ToArray()[0];
            var tfg1 = rf.Products.ToArray()[0];

            var ins1 = rf.Products;
            var ins2 = rf.Products;

            SimpleRepository sr = new SimpleRepository();
            var instance1 = sr.Products;
            var instance2 = sr.Products;
        //Custome made products from ModelCompleteFakeRepository internal class
         Assert.True(tfg == tfg1);
         Assert.Equal(ins1, ins2);
        // products from Models.Products
        Assert.Equal(instance1, instance2);


        }
    }
}
