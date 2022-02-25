#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>

#include <iostream>

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

void Filtrage(const cv::Mat& src, cv::Mat& dst, int filtreType, int argument) {

}


int main(int argc, char const *argv[]) {
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

    return 0;
}
