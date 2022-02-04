#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>

#include <iostream>

void draw_squareV1(cv::Mat &image, int size) {
    for (int i = (image.rows - size) / 2; i < (image.rows + size) / 2; ++i) {
        for (int j = (image.cols - size) / 2; j < (image.cols + size) / 2; ++j) {
            image.at<cv::Vec3b>(i, j)[0] = 0;
            image.at<cv::Vec3b>(i, j)[1] = 255;
            image.at<cv::Vec3b>(i, j)[2] = 0;
        }
    }
}

void draw_squareV2(cv::Mat &image, int size) {
    for (int i = (image.rows - size) / 2; i < (image.rows + size) / 2; ++i) {
        for (int j = (image.cols - size) / 2; j < (image.cols + size) / 2; ++j) {
            for (int c = 0; c < 3; ++c) {
                image.at<cv::Vec3b>(i, j)[c] = 255 * (i - (image.rows - size) / 2) / size;
            }
        }
    }
}

void draw_circle(cv::Mat &image, int x, int y, int radius) {
    for (int i = -radius; i < radius; ++i) {
        for (int j = -radius; j < radius; ++j) {
            if (i * i + j * j < radius * radius) {
                image.at<cv::Vec3b>(x + i, y + j)[0] = 0;
                image.at<cv::Vec3b>(x + i, y + j)[1] = 255;
                image.at<cv::Vec3b>(x + i, y + j)[2] = 255;
            }
        }
    }
    // with openCV function
    // cv::circle(image, cv::Point2i(x, y), radius, cv::Scalar(0,255,255), -1);
}

//////////////////////////////////////////////////////////////////////////////////////////////////
int main(int argc, char **argv) {
    // check arguments
    if (argc != 2) {
        std::cout << "usage: " << argv[0] << " image" << std::endl;
        return -1;
    }

    // load the input image
    std::cout << "load image ..." << std::endl;
    cv::Mat image = cv::imread(argv[1]);
    if (image.empty()) {
        std::cout << "error loading " << argv[1] << std::endl;
        return -1;
    }
    std::cout << "image size : " << image.cols << " x " << image.rows << std::endl;

    /*draw_squareV1(image, 50);*/
    draw_squareV2(image, 50);
    draw_circle(image, 100, 100, 50);

    // display an image
    cv::imshow("une image", image);
    std::cout << "appuyer sur une touche ..." << std::endl;
    cv::waitKey();

    // save the image
    cv::imwrite("output/tp1ex3.jpg", image);

    return 1;
}
