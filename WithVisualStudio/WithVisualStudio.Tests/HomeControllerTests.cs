using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Linq;
using WithVisualStudio.Controllers;
using WithVisualStudio.Models;
using Xunit;
using Moq;

namespace WithVisualStudio.Tests
{
    public class HomeControllerTests
    {
        [Theory]
        [ClassData(typeof(ProductTestData))]
        public void IndexActionModelIsComplete4(Product[] products)
        {
            // Arrange
            var mock = new Mock<IRepository>();
            mock.SetupGet(m => m.Products).Returns(products);
            var controller = new HomeController { Repository = mock.Object };
            // Act
            var model = (controller.Index() as ViewResult)?.ViewData.Model
            as IEnumerable<Product>;
            // Assert
            Assert.Equal(controller.Repository.Products, model,
            Comparer.Get<Product>((p1, p2) => p1.Name == p2.Name
            && p1.Price == p2.Price));
        }
        class ModelCompleteFakeRepository : IRepository
        {
            public IEnumerable<Product> Products1 { get; set; }
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
        class PropertyOnceFakeRepository : IRepository
        {

            public int PropertyCounter { get; set; } = 0;
            public IEnumerable<Product> Products
            {
                get
                {
                    PropertyCounter++;
                    return new[] { new Product { Name = "P1", Price = 100 } };
                }
            }
            public void AddProduct(Product p)
            {
                // do nothing - not required for test
            }
        }
        [Fact]
        public void RepositoryPropertyCalledOnce()
        {
            var mock = new Mock<IRepository>();
            mock.SetupGet(m => m.Products)
            .Returns(new[] { new Product { Name = "P1", Price = 100 } });
            var controller = new HomeController { Repository = mock.Object };
            var result = controller.Index();
            mock.VerifyGet(m => m.Products, Times.Once);
            
            
            // Arrange
            var repo = new PropertyOnceFakeRepository();
            var controller1 = new HomeController { Repository = repo };
            // Act
            var result1 = controller1.Index();
            // Assert
            Assert.Equal(1, repo.PropertyCounter);
        }

        [Fact]
        public void IndexActionModelIsComplete()
        {

            ModelCompleteFakeRepository rf = new ModelCompleteFakeRepository();
            var tfg = rf.Products.ToArray()[0];
            var tfg1 = rf.Products.ToArray()[0];

            var ins1 = rf.Products;
            var ins2 = rf.Products;

            Assert.True(tfg == tfg1);
            Assert.Equal(ins1, ins2);

        }
        [Fact]
        public void IndexActionModelIsComplete1()
        {
            SimpleRepository sr = new SimpleRepository();
            var instance1 = sr.Products;
            var instance2 = sr.Products;
            // products from Models.Products
            Assert.Equal(instance1, instance2);

        }
        [Theory]
        [InlineData(275, 48.95, 19.50, 24.95)]
        [InlineData(5, 48.95, 19.50, 24.95)]
        public void IndexActionModelIsComplete2(decimal price1, decimal price2,
              decimal price3, decimal price4)
        {
            var controller = new HomeController();
            controller.Repository = new ModelCompleteFakeRepository
            {
                Products1 = new Product[] {
                        new Product {Name = "P1", Price = price1 },
                        new Product {Name = "P2", Price = price2 },
                        new Product {Name = "P3", Price = price3 },
                        new Product {Name = "P4", Price = price4 },
                        }
            };
            var model = (controller.Index() as ViewResult)?.ViewData.Model
                         as IEnumerable<Product>;
            // Assert
            Assert.Equal(controller.Repository.Products, model,
            Comparer.Get<Product>((p1, p2) => p1.Name == p2.Name
            && p1.Price == p2.Price));
        }
        [Theory]
        [ClassData(typeof(ProductTestData))]
        public void IndexActionModelIsComplete3(Product[] products)
        {
            // Arrange
            var controller = new HomeController();
            controller.Repository = new ModelCompleteFakeRepository
            {
                Products1 = products
            };
            // Act
            var model = (controller.Index() as ViewResult)?.ViewData.Model
            as IEnumerable<Product>;
            // Assert
            Assert.Equal(controller.Repository.Products, model,
            Comparer.Get<Product>((p1, p2) => p1.Name == p2.Name
            && p1.Price == p2.Price));
        }
    }
}
