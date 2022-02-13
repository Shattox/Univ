#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>

#include <iostream>

using namespace cv;

//////////////////////////////////////////////////////////////////////////////////////////////////
void callBackKeyboard(int event, int x, int y, int flags, void *userdata) {
    switch (event) {
        case cv::EVENT_LBUTTONDOWN :
            std::cout << "left button pressed at : " << x << ", " << y << std::endl;
            break;

        case cv::EVENT_RBUTTONDOWN :
        case cv::EVENT_MBUTTONDOWN :
        case cv::EVENT_MOUSEMOVE   :
            break;
    }
}

void extrapolation(const cv::Mat &image, int size) {
    cv::Mat newImage = cv::Mat(3 * image.rows, 3 * image.cols, image.type());

    /*for (int k = 0; k < size; ++k) {
        for (int l = 0; l < size; ++l) {
            for (int i = 0; i < image.rows; ++i) {
                for (int j = 0; j < image.cols; ++j) {
                    auto const pixel = image.at<cv::Vec3b>(i, j);
                    int jInRect = (j != 1) ? l * image.cols + image.cols - j - 1 : l * image.cols + j;
                    int iInRect = (i != 1) ? k * image.rows + image.rows - i - 1 : k * image.rows + i;
                    newImage.at<cv::Vec3b>(iInRect, jInRect) = pixel;
                }
            }
        }
    }*/
    for (int k = 0; k < 3; ++k) {
        for (int l = 0; l < 3; ++l) {
            for (int i= 0; i < image.rows; ++i) {
                for (int j = 0; j < image.cols; ++j) {
                    auto const pixel = image.at<Vec3b>(i, j);
                    int jInRect = (j != 1) ? l * image.cols + image.cols - j - 1 : l * image.cols + j;
                    int iInRect = (i != 1) ? k * image.rows + image.rows - i - 1 : k * image.rows + i;
                    newImage.at<Vec3b>(iInRect, jInRect) = pixel;
                }
            }
        }
    }
    cv::imshow("newImage", newImage);
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

    // setup a window
    cv::namedWindow("image", 1);
    cv::setMouseCallback("image", callBackKeyboard, NULL);


    bool loopOn = true;

    while (loopOn) {

        cv::imshow("image", image);
        extrapolation(image, 3);

        int key = cv::waitKey(500) % 256;
        if (key == 27 || key == 'q')
            loopOn = false;
    }


    return 1;
}
